package com.ruoyi.common.utils;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.Message;
import com.microsoft.graph.models.Recipient;
import com.microsoft.graph.models.BodyType;
import com.microsoft.graph.models.ItemBody;
import com.microsoft.graph.models.EmailAddress;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.models.UserSendMailParameterSet;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class AzureEmailUtils {

    private static String clientId;
    private static String clientSecret;
    private static String tenantId;
    private static String userId;

    private static ResourceLoader resourceLoader;

    @Value("${azure.client-id}")
    public void setClientId(String clientId) {
        AzureEmailUtils.clientId = clientId;
    }

    @Value("${azure.client-secret}")
    public void setClientSecret(String clientSecret) {
        AzureEmailUtils.clientSecret = clientSecret;
    }

    @Value("${azure.tenant-id}")
    public void setTenantId(String tenantId) {
        AzureEmailUtils.tenantId = tenantId;
    }

    @Value("${azure.user-id}")
    public void setUserId(String userId) {
        AzureEmailUtils.userId = userId;
    }

    @Autowired
    public void setResourceLoader(ResourceLoader resourceLoader) {
        AzureEmailUtils.resourceLoader = resourceLoader;
    }

    private static GraphServiceClient<Request> graphClient;

    @PostConstruct
    private void init() {
        ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .tenantId(tenantId)
                .build();

        TokenCredentialAuthProvider authProvider = new TokenCredentialAuthProvider(
                Collections.singletonList("https://graph.microsoft.com/.default"), clientSecretCredential);

        graphClient = GraphServiceClient.builder()
                .authenticationProvider(authProvider)
                .buildClient();
    }

    public static void sendEmail(String recipientEmail, String subject, String content) {
        Message message = new Message();
        message.subject = subject;

        ItemBody body = new ItemBody();
        body.contentType = BodyType.TEXT;
        body.content = content;
        message.body = body;

        Recipient recipient = new Recipient();
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.address = recipientEmail;
        recipient.emailAddress = emailAddress;
        message.toRecipients = Collections.singletonList(recipient);

        UserSendMailParameterSet parameters = UserSendMailParameterSet
                .newBuilder()
                .withMessage(message)
                .withSaveToSentItems(false)
                .build();

        graphClient.users(userId).sendMail(parameters).buildRequest().post();
    }

    public static void sendHtmlEmail(String recipientEmail, String subject, String htmlContent) {
        Message message = new Message();
        message.subject = subject;

        ItemBody body = new ItemBody();
        body.contentType = BodyType.HTML;
        body.content = htmlContent;
        message.body = body;

        Recipient recipient = new Recipient();
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.address = recipientEmail;
        recipient.emailAddress = emailAddress;
        message.toRecipients = Collections.singletonList(recipient);

        UserSendMailParameterSet parameters = UserSendMailParameterSet
                .newBuilder()
                .withMessage(message)
                .withSaveToSentItems(false)
                .build();

        graphClient.users(userId).sendMail(parameters).buildRequest().post();
    }

    public static void sendHtmlEmail(String recipientEmail, String subject, EmailTemplate template, Object... args) {
        String htmlContent = loadTemplate(template.getTemplatePath());
        htmlContent = replacePlaceholders(htmlContent, args);
        sendHtmlEmail(recipientEmail, subject, htmlContent);
    }

    private static String loadTemplate(String templatePath) {
        try {
            Resource resource = resourceLoader.getResource(templatePath);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append(System.lineSeparator());
                }
                return content.toString();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load email template！！！", e);
        }
    }

    private static String replacePlaceholders(String template, Object... args) {
        for (int i = 0; i < args.length; i++) {
            String value = args[i] != null ? args[i].toString() : "";
            template = template.replace("${" + i + "}", value);
        }
        return template;
    }

    public enum EmailTemplate {
        NEW_ORDER_NOTIFICATION("classpath:templates/new_order_notification.html"),
        ASSIGN_ORDER_NOTIFICATION("classpath:templates/assign_order_notification.html"),
        ORDER_ASSIGNED_NOTIFICATION("classpath:templates/order_assigned_notification.html"),
        ORDER_INITIATED_NOTIFICATION("classpath:templates/order_initiated_notification.html"),
        ORDER_COMPLETED_NOTIFICATION("classpath:templates/order_completed_notification.html"),
        ORDER_INCOMPLETE_NOTIFICATION("classpath:templates/order_incomplete_notification.html"),
        COMPLAINT_ORDER_ACCEPTED_NOTIFICATION("classpath:templates/complaint_order_accepted_notification.html"),
        COMPLAINT_ORDER_FEEDBACK_NOTIFICATION("classpath:templates/complaint_order_feedback_notification.html");

        private final String templatePath;

        EmailTemplate(String templatePath) {
            this.templatePath = templatePath;
        }

        public String getTemplatePath() {
            return templatePath;
        }
    }
}
