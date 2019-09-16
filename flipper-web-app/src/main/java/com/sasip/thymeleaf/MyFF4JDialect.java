package com.sasip.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import org.ff4j.FF4j;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;

/**
 * Created by benoitmeriaux on 08/01/15.
 */
public class MyFF4JDialect extends AbstractProcessorDialect
{

    private FF4j ff4j;

    public MyFF4JDialect()
    {
        super("FF4JDialect", "ff4j", 10);
    }

    public void setFF4J(FF4j ff4j)
    {
        this.ff4j = ff4j;
    }

    //
    // The processors.
    //
    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix)
    {
        final Set<IProcessor> processors = new HashSet<IProcessor>();
        processors.add(new MyFF4jEnableAttrProcessor(dialectPrefix, ff4j));
        processors.add(new MyFF4jDisableAttrProcessor(dialectPrefix, ff4j));
        return processors;

    }
}
