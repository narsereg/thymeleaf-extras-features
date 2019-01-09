package org.narsereg.features

import org.thymeleaf.dialect.AbstractProcessorDialect
import org.thymeleaf.processor.IProcessor

class FeaturesDialect : AbstractProcessorDialect("Features Dialect", "features", 1000) {
    override fun getProcessors(dialectPrefix: String): MutableSet<IProcessor> {
        val processors = mutableSetOf<IProcessor>()
        processors.add(EnabledTagProcessor(prefix))
        processors.add(DisabledTagProcessor(prefix))

        return processors
    }
}