package com.example.weatherforecast.designsystem.textstyle

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.weatherforecast.R

internal val poppinsFontFamily = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_bold, FontWeight.Bold)
)

val defaultTextStyle = WeatherTextStyle(
    display = Display(
        xl = TextStyle(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
        )
    ),
    title = Title(
        xl = TextStyle(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp
        ),
        lg = TextStyle(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        ),
        md = TextStyle(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp
        ),
        sm = TextStyle(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )
    ),
    body = Body(
        lg = Weight(
            regular = TextStyle(
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            ),
            medium = TextStyle(
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            ),
            semiBold = TextStyle(
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        ),
        md = Weight(
            regular = TextStyle(
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.sp,
                fontSize = 14.sp
            ),
            medium = TextStyle(
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.sp,
                fontSize = 14.sp
            ),
            semiBold = TextStyle(
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.sp,
                fontSize = 14.sp
            )
        ),
        sm = Weight(
            regular = TextStyle(
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.sp,
                fontSize = 12.sp
            ),
            medium = TextStyle(
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.sp,
                fontSize = 12.sp
            ),
            semiBold = TextStyle(
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.sp,
                fontSize = 12.sp
            )
        )
    ),
    label = Label(
        md = Weight(
            regular = TextStyle(
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.sp,
                fontSize = 12.sp
            ),
            medium = TextStyle(
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.sp,
                fontSize = 12.sp
            ),
            semiBold = TextStyle(
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.sp,
                fontSize = 12.sp
            )
        )
    )
)
