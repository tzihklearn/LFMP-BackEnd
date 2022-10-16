package com.MinNiCup.lfmpbackend.utils.WebSocket;

import com.MinNiCup.lfmpbackend.mapper.ConsultMapper;
import com.MinNiCup.lfmpbackend.pojo.domain.Consult;
import com.MinNiCup.lfmpbackend.pojo.domain.User;
import com.MinNiCup.lfmpbackend.pojo.dto.param.WsConnectParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Objects;

@Component
@Slf4j
public class ConsultUtil {
    private static final String LAWYERP = "PLAWYERP";
    private static final String USERP = "PUSERP";
    @Resource
    private ConsultMapper consultMapper;

    private static ConsultUtil consultUtil;

    @PostConstruct
    public void init() {
        consultUtil = this;
        consultUtil.consultMapper = this.consultMapper;
    }

    public static Consult checkUserAndConsult(WsConnectParam param, User user){
        Consult consult = consultUtil.consultMapper.selectByConsultId(param.getConsultId());
        if (consult == null)
            return consult;
        if (!Objects.equals(consult.getConsumerId(), user.getIsIdent() == 2 ? consult.getLawyerId() : consult.getConsumerId()))
            consult.setId(0);
        return consult;
    }


    public static String getSessionCode(Consult consult, User user, Boolean isMe){
        if (isMe) {
            if (user.getIsIdent() == 2)
                return "B" + user.getId() + LAWYERP + consult.getId();
            else
                return "C" + consult.getId() + USERP + user.getId();
        } else {
            if (user.getIsIdent() == 2)
                return "C" + consult.getId() + USERP + consult.getConsumerId();
            else
                return "B" + consult.getLawyerId() + LAWYERP + consult.getId();
        }
    }
}
