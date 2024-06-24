package org.narsereg.features

import org.togglz.core.Feature
import org.togglz.core.metadata.EmptyFeatureMetaData
import org.togglz.core.metadata.FeatureMetaData
import org.togglz.core.spi.FeatureProvider

class DummyFeatureProvider(
    private val feature: Feature,
) : FeatureProvider {
    override fun getFeatures(): Set<Feature> = setOf(feature)

    override fun getMetaData(feature: Feature): FeatureMetaData = EmptyFeatureMetaData(feature)
}
