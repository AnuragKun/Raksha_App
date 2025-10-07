package com.arlabs.raksha.Common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import com.arlabs.raksha.R

@Composable
fun GradientBox(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val gradientColors: List<Color> = listOf(
        colorResource(id = R.color.pink1),
        colorResource(id = R.color.pink2),
        colorResource(id = R.color.pink3)
    )

    Box(
        modifier = modifier
            .background(brush = Brush.verticalGradient(colors = gradientColors)),
        content = content
    )
}


class TopCurveShape: Shape{
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ) : Outline {
        val path = Path().apply {
            val curveStartHeight = size.height * 0.15f
            moveTo(0f,size.height)
            lineTo(0f,curveStartHeight)
            quadraticTo(x1 = size.width / 2,      // Control point X: middle
                y1 = curveStartHeight,    // Control point Y: bottom of the curve
                x2 = size.width,          // End point X: right edge
                y2 = 0f)
            lineTo(size.width,size.height)
            close()
        }
    return Outline.Generic(path)
    }
}