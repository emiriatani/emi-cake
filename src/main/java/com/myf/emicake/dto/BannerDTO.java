package com.myf.emicake.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @ClassName com.myf.emicake.dto BannerDTO
 * @Description
 * @Author Afengis
 * @Date 2021/2/19 14:16
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BannerDTO  implements Serializable {

    private static final long serialVersionUID = 1028573558265854676L;

    @NotEmpty(message = "商品id不能为空")
    private Integer id;

    private String name;

    @NotBlank(message = "banner url不能为空")
    @URL(message = "banner url获取异常")
    private String imgBanner;
}
