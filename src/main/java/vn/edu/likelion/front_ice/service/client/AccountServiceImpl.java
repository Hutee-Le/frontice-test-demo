package vn.edu.likelion.front_ice.service.client;

import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import vn.edu.likelion.front_ice.common.enums.Role;
import vn.edu.likelion.front_ice.common.exceptions.AppException;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;
import vn.edu.likelion.front_ice.dto.request.LoginRequest;
import vn.edu.likelion.front_ice.dto.request.RegisterRequest;
import vn.edu.likelion.front_ice.dto.response.LoginResponse;
import vn.edu.likelion.front_ice.dto.response.RegisterResponse;
import vn.edu.likelion.front_ice.entity.AccountEntity;
import vn.edu.likelion.front_ice.entity.ChallengerEntity;
import vn.edu.likelion.front_ice.entity.RecruiterEntity;
import vn.edu.likelion.front_ice.mapper.AccountMapper;
import vn.edu.likelion.front_ice.mapper.ChallengerMapper;
import vn.edu.likelion.front_ice.repository.AccountRepository;
import vn.edu.likelion.front_ice.repository.ChallengerRepository;
import vn.edu.likelion.front_ice.repository.RecruiterRepository;
import vn.edu.likelion.front_ice.security.SecurityUtil;

import java.util.*;

/**
 * AccountServiceImpl -
 *
 * @param
 * @return
 * @throws
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ChallengerRepository challengerRepository;

    @Autowired
    RecruiterRepository recruiterRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    SecurityUtil securityUtil;
    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    // stores reset token
    private Map<String, String> resetTokenStorage = new HashMap<>();

    // stores expiration reset token
    private Map<String, Long> resetTokenExpiry = new HashMap<>();

    private final long RESET_TOKEN_EXPIRY_DURATION = 10 * 60 * 1000;

    @Override
    public Optional<RegisterResponse> create(RegisterRequest registerRequest) {

        if (accountRepository.findByEmail(registerRequest.getEmail()).isEmpty()) {
            if (registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
                AccountEntity accountEntity = accountMapper.toAccount(registerRequest);
                accountEntity.setPassword(passwordEncoder.encode(accountEntity.getPassword()));
                accountEntity.setIsDeleted(0);
                accountEntity.setStatus(1);

                accountRepository.save(accountEntity);

                switch (registerRequest.getRole()) {
                    case CHALLENGER -> {
                        accountEntity.setRole(Role.CHALLENGER);

                        // create challenger profile
                        ChallengerEntity challengerEntity = ChallengerEntity.builder()
                                .accountId(accountEntity.getId())
                                .build();

                        challengerRepository.save(challengerEntity);
                    }
                    case RECRUITER -> {
                        accountEntity.setRole(Role.RECRUITER);

                        // create challenger profile
                        RecruiterEntity recruiterEntity = RecruiterEntity.builder()
                                .accountId(accountEntity.getId())
                                .build();

                        recruiterRepository.save(recruiterEntity);
                    }
                    default -> throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
                }


                return Optional.of(accountMapper.toRegisterResponse(accountEntity));
            } else {
                throw new AppException(ErrorCode.CONFIRM_PASSWORD_NOT_MATCH);
            }

        } else {
            throw new AppException(ErrorCode.ACCOUNT_EXIST);
        }


    }

    @Override
    public Optional<RegisterResponse> updateInfo(String id, RegisterRequest registerRequest) {
        return Optional.empty();
    }

    @Override
    public List<RegisterResponse> saveAll(List<AccountEntity> ts) {
        return List.of();
    }


    @Override
    public void delete(String id) {

    }

    @Override
    public void deleteAll(List<String> listId) {

    }

    @Override
    public Optional<RegisterResponse> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<RegisterResponse> findAll() {

        return List.of();
    }

    @Override
    public Optional<LoginResponse> login(LoginRequest loginRequest) {


        AccountEntity accountEntity = accountRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_OR_PASSWORD_INCORRECT)); // customize lại ErrorCode

        if (accountEntity.getIsAuthenticated() == 0) {
            throw new AppException(ErrorCode.ACCOUNT_UNAUTHENTICATED);
        }

        // Nạp input gồm username/password vào Security
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        LoginResponse loginResponse = LoginResponse.builder()
                .accessToken(securityUtil.createAccessToken(authentication))
                .account(accountEntity)
                .expiresIn(securityUtil.getExpirationTime())
                .build();


        return Optional.of(loginResponse);
    }

    private Map<String, String> otpStorage = new HashMap<>(); // Lưu OTP theo email
    private Random random = new Random();

    // Tạo OTP và lưu vào map tạm thời
    public String generateOTP(String email) {
        String otp = String.format("%06d", random.nextInt(999999));

        //---------------------
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setTo(email);
            helper.setSubject("Xác nhận Email");

            Context context = new Context();
            context.setVariable("subject", "Welcome you go to my system!");
            context.setVariable("message", "Here is your OTP: " + otp);

            // build trang html thymeleaf de lam noi dung mail
            String htmlContent = templateEngine.process("sendOTP", context);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi gửi email xác nhận";
        }
        //---------------------

        otpStorage.put(email, otp);

        return "OTP da duoc gui ve mail";
    }

    // Xác thực OTP nhập vào
    public boolean verifyOTP(String email, String otp) {
        return otp.equals(otpStorage.get(email));
    }

    // Xóa OTP sau khi xác thực thành công
    public void clearOTP(String email) {
        AccountEntity accountEntity = accountRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST));

        accountEntity.setIsAuthenticated(1);

        accountRepository.save(accountEntity);
        otpStorage.remove(email);
    }

    public void sendEmailWithTemplate(String email, String subject, String templateName, Map<String, Object> variables) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setTo(email);
            helper.setSubject(subject);

            Context context = new Context();
            variables.forEach(context::setVariable);  // Đặt các biến vào context

            String htmlContent = templateEngine.process(templateName, context);
            helper.setText(htmlContent, true);  // true là để gửi nội dung HTML

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(ErrorCode.EMAIL_SENDING_FAILED);
        }
    }

    // create otp forgot password
    @Override
    public String generateForgotPasswordOTP(String email) {
        String otp = String.format("%06d", random.nextInt(999999));

        otpStorage.put(email, otp);

        Map<String, Object> variables = new HashMap<>();
        variables.put("otp", otp);

        sendEmailWithTemplate(email, "Forgot Password OTP", "forgotPasswordOTP", variables);

        return "OTP cho quên mật khẩu đã được gửi về mail.";
    }

    @Override
    public String generateResetToken(String email) {
        String resetToken = UUID.randomUUID().toString();
        resetTokenStorage.put(email, resetToken);
        resetTokenExpiry.put(email, System.currentTimeMillis() + RESET_TOKEN_EXPIRY_DURATION); // Lưu thời gian hết hạn
        return resetToken;
    }

    @Override
    public boolean verifyForgotPasswordOTP(String email, String otp) {
        String storedOtp = otpStorage.get(email);

        if (storedOtp != null && storedOtp.equals(otp)) {
            otpStorage.remove(email);
            return true;
        }

        return false;
    }

    @Override
    public boolean resetPassword(String resetToken, String newPassword) {
        // Kiểm tra và tìm email bằng resetToken
        String email = validateResetToken(resetToken);

        // Tìm tài khoản theo email
        AccountEntity account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST));

        // Mã hóa mật khẩu mới và lưu thay đổi
        account.setPassword(passwordEncoder.encode(newPassword));
        accountRepository.save(account);

        // Xóa reset token
        clearResetToken(email);

        return true;
    }

    @Override
    public void updateAccountToken(String token, String email) {
       Optional<AccountEntity> currentAccount = accountRepository.findByEmail(email);

       if (currentAccount.isPresent()) {
           AccountEntity account = currentAccount.get();
           account.setRefreshToken(token);
           accountRepository.save(account);
       }
    }

    @Override
    public Optional<LoginResponse> refreshToken(String refreshToken) {
        Jwt decodedToken = securityUtil.checkValidRefreshToken(refreshToken);
        String email = decodedToken.getSubject();

        AccountEntity account = accountRepository.findByEmailAndRefreshToken(email, refreshToken)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_REFRESH_TOKEN));

        // new access token
        String newAccessToken = securityUtil.createAccessTokenFromEmail(email);
        long expiresIn = securityUtil.getExpirationTime();

        // new refresh token
        String newRefreshToken = securityUtil.createRefreshToken(email, null);
        account.setRefreshToken(newRefreshToken);
        updateAccountToken(newRefreshToken, email);

        return Optional.ofNullable(LoginResponse.builder()
                .accessToken(newAccessToken)
                .expiresIn(securityUtil.getExpirationTime())
                .account(account)
                .build());
    }

    @Override
    public void clearRefreshToken(String email) {
        Optional<AccountEntity> accountOptional = accountRepository.findByEmail(email);

        if (accountOptional.isPresent()) {
            AccountEntity account = accountOptional.get();
            account.setRefreshToken(null);
            accountRepository.save(account);
        } else {
            throw new AppException(ErrorCode.ACCOUNT_NOT_EXIST);
        }
    }

    private String validateResetToken(String resetToken) {
        // Kiểm tra token và trả về email
        return resetTokenStorage.entrySet().stream()
                .filter(entry -> entry.getValue().equals(resetToken))
                .findFirst()
                .map(entry -> {
                    String email = entry.getKey();
                    if (resetTokenExpiry.get(email) > System.currentTimeMillis()) {
                        return email;
                    } else {
                        clearResetToken(email);
                        throw new AppException(ErrorCode.RESET_TOKEN_EXPIRED);
                    }
                })
                .orElseThrow(() -> new AppException(ErrorCode.RESET_TOKEN_INVALID));
    }

    private void clearResetToken(String email) {
        resetTokenStorage.remove(email);
        resetTokenExpiry.remove(email);
    }

    @Override
    public Optional<AccountEntity> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }
}
