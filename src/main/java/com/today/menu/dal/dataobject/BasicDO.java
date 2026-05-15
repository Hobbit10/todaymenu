package com.today.menu.dal.dataobject;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(value = "transMap")
public abstract class BasicDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdon;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime lastchangedon;

    @TableField(fill = FieldFill.INSERT)
    private String createdby;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String lastchangedby;

    @TableLogic
    private Boolean deleted;
}