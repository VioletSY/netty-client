package com.googosoft.initializer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.googosoft.model.Dispacher;

/**
 * @author songyan
 * @date 2020年4月23日 下午8:27:19
 * @desc 项目启动之后执行线程调度器
 */
@Component
@Order(1)
public class ApplicationInitor implements CommandLineRunner {
     
    @Override
    public void run(String... args) throws Exception {
    	Dispacher.run();
    }
    
}