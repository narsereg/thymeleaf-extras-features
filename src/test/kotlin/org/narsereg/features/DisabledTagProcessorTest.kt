package org.narsereg.features

import DummyFeatureProvider
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.thymeleaf.context.ITemplateContext
import org.thymeleaf.engine.AttributeName
import org.thymeleaf.model.IProcessableElementTag
import org.thymeleaf.processor.element.IElementTagStructureHandler
import org.togglz.core.Feature
import org.togglz.core.context.FeatureContext
import org.togglz.core.manager.FeatureManagerBuilder

@ExtendWith(MockKExtension::class)
internal class DisabledTagProcessorTest {
    private val processor = DisabledTagProcessor("features")

    @RelaxedMockK
    lateinit var context: ITemplateContext

    @RelaxedMockK
    lateinit var tag: IProcessableElementTag

    @RelaxedMockK
    lateinit var handler: IElementTagStructureHandler

    @BeforeEach
    fun before() {
        mockkStatic(FeatureContext::class)
        mockkObject(FeatureValidator.Companion)

        val featureManager =
            FeatureManagerBuilder.begin().featureProvider(DummyFeatureProvider(Feature { "feature" })).build()
        every { FeatureContext.getFeatureManager() } returns featureManager
        every { tag.getAttributeValue(any<AttributeName>()) } returns "FEATURE"
    }

    @Test
    fun shouldNotRemoveTagIfDisabled() {
        every { FeatureValidator.isActive("FEATURE") } returns false

        processor.process(context, tag, handler)

        verify(exactly = 0) { handler.removeBody() }
    }

    @Test
    fun shouldRemoveTagIfEnabled() {
        every { FeatureValidator.isActive("FEATURE") } returns true

        processor.process(context, tag, handler)

        verify(exactly = 1) { handler.removeBody() }
    }
}
