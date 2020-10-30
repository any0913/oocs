package api.dongsheng.task;

import api.dongsheng.model.entity.PaymentOrder;
import api.dongsheng.service.PayOrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 支付统计定时任务
 */
@EnableScheduling
@Component
@Slf4j
public class MailTask {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private PayOrdersService payOrdersService;


    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.recipient}")
    private String recipient;

    @Value("${sendEmail}")
    private boolean sendEmail;

    @Scheduled(cron = "0 0 9 * * ?")
    public void MailTask() {
        if(sendEmail){
            log.info(System.currentTimeMillis() + "检测支付情况开始!");
            StringBuffer sb = new StringBuffer();
            // 获取每天赠送会员数
            Integer giveNum = payOrdersService.selectGiveMemberNum();
            sb.append("赠送会员数统计：").append("\r\n");
            sb.append(">>山灵领取:"+giveNum+"个").append("\r\n");
            // 获取每天各方式的支付总金额
            sb.append("支付金额统计：").append("\r\n");
            List<PaymentOrder> list = payOrdersService.selectPaymentTotalMoney();
            if(list.size() > 0){
                for(PaymentOrder order : list){
                    Integer payType = order.getPayType().intValue();
                    Integer paymentAmount = order.getPaymentAmount();
                    switch (payType){
                        case 10 : sb.append(">>支付宝支付总金额："+paymentAmount/100).append("\r\n");break;
                        case 20 : sb.append(">>微信支付总金额："+paymentAmount/100).append("\r\n");break;
                        default:break;
                    }
                }
            }else{
                sb.append(">>未产生支付金额").append("\r\n");
            }
            // 内容
            String content = sb.toString();
            String subject = "支付统计";
            sendSimpleMail(subject,content);
            log.info(System.currentTimeMillis() + "检测支付情况结束!");
        }else{
            log.info(">>>>>>>>>>>>>>发送邮件定时任务关闭>>>>>>>>>>>>>>>>>>>>>");
        }
    }

    /**
     * 发送邮件
     * @param subject  邮件主题
     * @param content  邮件内容
     */
    private void sendSimpleMail(String subject,String content) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            //邮件发送人
            simpleMailMessage.setFrom(username);
            //邮件接收人
            simpleMailMessage.setTo(recipient);
            //邮件主题
            simpleMailMessage.setSubject(subject);
            //邮件内容
            simpleMailMessage.setText(content);
            log.info(">>>>>>>>>发送邮件："+simpleMailMessage.toString());
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            log.info("邮件发送失败:"+e.getLocalizedMessage());
        }
    }
}
