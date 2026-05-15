package com.today.menu.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createdon", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "lastchangedon", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "createdby", String.class, "");
        this.strictInsertFill(metaObject, "lastchangedby", String.class, "");
        this.strictInsertFill(metaObject, "deleted", Boolean.class, false);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "lastchangedon", LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, "lastchangedby", String.class, "");
    }
}