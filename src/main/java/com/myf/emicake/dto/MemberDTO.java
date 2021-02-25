package com.myf.emicake.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName com.myf.emicake.dto MemberDTO
 * @Description
 * @Author Afengis
 * @Date 2021/2/25 19:32
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO  implements Serializable {
    private static final long serialVersionUID = -1321236966870913531L;

    /**
     * 会员id
     */
    private Integer id;

    /**
     * 会员用户名
     */
    private String memberId;

}
