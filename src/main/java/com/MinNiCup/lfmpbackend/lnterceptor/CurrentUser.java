package com.MinNiCup.lfmpbackend.lnterceptor;

import lombok.Data;

import java.io.Serializable;

/**
 * @author tzih
 * @date 2022.10.13
 */
@Data
public class CurrentUser {

    Integer id;

    String account;

    Integer idIdent;

}
