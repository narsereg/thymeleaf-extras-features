package org.narsereg.features

import org.thymeleaf.context.ITemplateContext
import org.thymeleaf.engine.AttributeName
import org.thymeleaf.model.IProcessableElementTag
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor
import org.thymeleaf.processor.element.IElementTagStructureHandler
import org.thymeleaf.templatemode.TemplateMode

class EnabledTagProcessor(prefix: String) :
    AbstractAttributeTagProcessor(TemplateMode.HTML, prefix, null, false, "enabled", true, 1001, true) {

    override fun doProcess(
        context: ITemplateContext,
        tag: IProcessableElementTag,
        attributeName: AttributeName,
        attributeValue: String,
        structureHandler: IElementTagStructureHandler
    ) {
        if (!FeatureValidator.isActive(attributeValue)) {
            structureHandler.removeBody()
        }
    }
}