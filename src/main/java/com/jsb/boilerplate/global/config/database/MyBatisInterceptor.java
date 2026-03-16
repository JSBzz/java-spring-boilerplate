package com.jsb.boilerplate.global.config.database;

import com.jsb.boilerplate.model.BaseEntity;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Properties;

@Component
@Intercepts({
    @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class MyBatisInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        Object parameter = invocation.getArgs()[1];

        if (parameter instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) parameter;
            LocalDateTime now = LocalDateTime.now();

            if (SqlCommandType.INSERT.equals(sqlCommandType)) {
                baseEntity.setCreatedAt(now);
                baseEntity.setUpdatedAt(now);
            } else if (SqlCommandType.UPDATE.equals(sqlCommandType)) {
                baseEntity.setUpdatedAt(now);
            }
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
