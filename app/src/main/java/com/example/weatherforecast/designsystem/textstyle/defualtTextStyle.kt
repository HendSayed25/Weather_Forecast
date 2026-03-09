package com.example.weatherforecast.designsystem.textstyle

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.weatherforecast.R
import retrofit2.http.Body

internal val poppinsFontFamily = FontFamily(
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semibold, FontWeight.SemiBold)
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
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp
        ),
        lg = TextStyle(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        ),
        md = TextStyle(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        ),
        sm = TextStyle(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
    )
)