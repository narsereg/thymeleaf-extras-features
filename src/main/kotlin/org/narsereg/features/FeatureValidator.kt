package org.narsereg.features

import org.togglz.core.context.FeatureContext

class FeatureValidator {
    companion object {
        fun isActive(featureName: String): Boolean {
            val manager = FeatureContext.getFeatureManager()
            val feature = manager.features.firstOrNull { it.name() == featureName }

            return feature == null || manager.isActive(feature)
        }
    }
}
