package com.joeykaan.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import org.junit.Test

class GapsDetectorTest : LintDetectorTest() {
    @Test
    fun testGaps_withMarginDivisbleByFour_returnsError() {
        lint()
            .files(
                xml(
                    "/res/layout/nav_view_header.xml",
                    """
                        <?xml version="1.0" encoding="utf-8"?>
                        <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
                            android:layout_width="match_parent"
                            android:layout_height="144dp"
                            android:background="@color/coolBlue">
                        
                            <ImageView
                                android:layout_width="88dp"
                                android:layout_height="88dp"
                                android:layout_marginStart="12dp"
                                android:src="@drawable/logo" />
                        
                        </FrameLayout>
                    """.trimIndent()
                )
            ).run()
            .expectErrorCount(1)
    }

    @Test
    fun testGaps_withMarginUsingGap_NoError() {
        lint()
            .files(
                xml(
                    "/res/layout/nav_view_header.xml",
                    """
                        <?xml version="1.0" encoding="utf-8"?>
                        <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
                            android:layout_width="match_parent"
                            android:layout_height="144dp"
                            android:background="@color/coolBlue">
                        
                            <ImageView
                                android:layout_width="88dp"
                                android:layout_height="88dp"
                                android:layout_marginStart="@dimen/gap_3"
                                android:src="@drawable/logo" />
                        
                        </FrameLayout>
                    """.trimIndent()
                )
            ).run()
            .expectClean()
    }

    @Test
    fun testGaps_withMarginZero_NoError() {
        lint()
            .allowMissingSdk()
            .files(
                xml(
                    "/res/layout/nav_view_header.xml",
                    """
                        <?xml version="1.0" encoding="utf-8"?>
                        <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
                            android:layout_width="match_parent"
                            android:layout_height="144dp"
                            android:background="@color/coolBlue">
                        
                            <ImageView
                                android:layout_width="88dp"
                                android:layout_height="88dp"
                                android:layout_marginStart="0dp"
                                android:src="@drawable/logo" />
                        
                        </FrameLayout>
                    """.trimIndent()
                )
            ).run()
            .expectClean()
    }

    @Test
    fun testGaps_withMarginHigherThan68_NoError() {
        lint()
            .allowMissingSdk()
            .files(
                xml(
                    "/res/layout/nav_view_header.xml",
                    """
                        <?xml version="1.0" encoding="utf-8"?>
                        <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
                            android:layout_width="match_parent"
                            android:layout_height="144dp"
                            android:background="@color/coolBlue">
                        
                            <ImageView
                                android:layout_width="88dp"
                                android:layout_height="88dp"
                                android:layout_marginStart="68dp"
                                android:src="@drawable/logo" />
                        
                        </FrameLayout>
                    """.trimIndent()
                )
            ).run()
            .expectClean()
    }

    override fun getDetector() = GapsDetector()

    override fun getIssues() = listOf(ISSUE_GAPS_FOR_MARGIN_PADDING)
}
