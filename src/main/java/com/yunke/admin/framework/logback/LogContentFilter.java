package com.yunke.admin.framework.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.AbstractMatcherFilter;
import ch.qos.logback.core.spi.FilterReply;
import cn.hutool.core.collection.CollUtil;
import com.yunke.admin.common.util.StrUtil;

import java.util.ArrayList;
import java.util.List;


public class LogContentFilter extends AbstractMatcherFilter<ILoggingEvent> {

    private static List<String> CONTENTS = new ArrayList<>();

    private String content;

    public LogContentFilter(){

    }

    public void setContent(String content){
        this.content = content;
        if(StrUtil.isNotBlank(content)){
            CONTENTS.addAll(CollUtil.newArrayList(content.split(",")));
        }
    }

    @Override
    public FilterReply decide(ILoggingEvent iLoggingEvent) {
        if (!this.isStarted()) {
            return FilterReply.NEUTRAL;
        } else {
            return isMatch(iLoggingEvent.getMessage()) ? this.onMatch : this.onMismatch;
        }
    }

    private boolean isMatch(String logContent){
        return CONTENTS.stream().anyMatch(item -> logContent.contains(item));
    }

    @Override
    public void start() {

        if (this.content != null) {
            super.start();
        }
    }
}
