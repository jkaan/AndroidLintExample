package com.joeykaan.lint

import com.android.SdkConstants
import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.*
import com.android.tools.lint.detector.api.Scope.Companion.ALL_RESOURCES_SCOPE
import org.w3c.dom.Attr

class GapsDetector : LayoutDetector() {
    override fun getApplicableAttributes(): Collection<String>? {
        return listOf(
            SdkConstants.ATTR_LAYOUT_MARGIN,
            SdkConstants.ATTR_LAYOUT_MARGIN_TOP,
            SdkConstants.ATTR_LAYOUT_MARGIN_BOTTOM,
            SdkConstants.ATTR_LAYOUT_MARGIN_START,
            SdkConstants.ATTR_LAYOUT_MARGIN_END
        )
    }

    override fun visitAttribute(context: XmlContext, attribute: Attr) {
        val matchResult = "([0-9]+)dp".toRegex().matchEntire(attribute.value)

        if (matchResult != null) {
            val (amountDp) = matchResult.destructured

            if (amountDp.toInt() != 0 && amountDp.toInt() % 4 == 0) {
                context.report(
                    ISSUE_GAPS_FOR_MARGIN_PADDING,
                    context.getLocation(attribute),
                    ISSUE_GAPS_FOR_MARGIN_PADDING.getExplanation(TextFormat.TEXT)
                )
            }
        }
    }
}

class LintRegistry : IssueRegistry() {
    override val api: Int
        get() = CURRENT_API

    override val issues: List<Issue>
        get() = listOf(ISSUE_GAPS_FOR_MARGIN_PADDING)
}

val ISSUE_GAPS_FOR_MARGIN_PADDING = Issue.create(
    id = "GapsForMarginPadding",
    briefDescription = "Gaps should be used for margin if divisible by fourasd",
    explanation = "Gaps should be used for margin if divisible by fourfdsa",
    category = Category.CORRECTNESS,
    priority = 5,
    severity = Severity.ERROR,
    implementation = Implementation(GapsDetector::class.java, ALL_RESOURCES_SCOPE)
)
