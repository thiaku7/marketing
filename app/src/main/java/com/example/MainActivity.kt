package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.ExpoMode
import com.example.ui.components.KnowledgeMode
import com.example.ui.components.SplashScreen
import com.example.ui.theme.*
import com.example.viewmodel.MarketingViewModel

class MainActivity : ComponentActivity() {
  private val viewModel: MarketingViewModel by viewModels()

  @OptIn(ExperimentalAnimationApi::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    
    setContent {
      MyApplicationTheme {
        val isSplashActive by viewModel.isSplashActiveState.collectAsState()

        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(BgDark)
        ) {
          AnimatedContent(
            targetState = isSplashActive,
            transitionSpec = {
              fadeIn(animationSpec = tween(400)) with fadeOut(animationSpec = tween(400))
            },
            label = "SplashTransition"
          ) { splashActive ->
            if (splashActive) {
              SplashScreen()
            } else {
              MainContentScaffold(viewModel = viewModel)
            }
          }
        }
      }
    }
  }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalAnimationApi::class)
@Composable
fun MainContentScaffold(viewModel: MarketingViewModel) {
  val currentTab by viewModel.currentTab.collectAsState()

  Scaffold(
    modifier = Modifier
      .fillMaxSize()
      .background(BgDark),
    topBar = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .background(BgDark)
          .statusBarsPadding()
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 14.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "marketing",
            color = TextCrema,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.ExtraLight,
            fontSize = 20.sp,
            letterSpacing = 4.sp
          )
          Box(
            modifier = Modifier
              .border(0.5.dp, BorderDark, RoundedCornerShape(4.dp))
              .background(CardBg)
              .padding(horizontal = 10.dp, vertical = 4.dp)
          ) {
            Text(
              text = if (currentTab == 0) "EXPOSICIÓN" else "CONOCIMIENTO",
              color = AccentTerracota,
              fontSize = 9.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 1.sp
            )
          }
        }
        Divider(color = BorderDark, thickness = 1.dp)
      }
    },
    bottomBar = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .background(BgDark)
          .navigationBarsPadding()
      ) {
        Divider(color = BorderDark, thickness = 1.dp)
        NavigationBar(
          containerColor = BgDark,
          tonalElevation = 0.dp,
          modifier = Modifier.height(64.dp)
        ) {
          // Tab 0: Experiencia
          NavigationBarItem(
            selected = currentTab == 0,
            onClick = { viewModel.setTab(0) },
            icon = {
              Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Experiencia",
                modifier = Modifier.size(20.dp)
              )
            },
            label = {
              Text(
                "Experiencia",
                fontSize = 10.sp,
                letterSpacing = 1.sp,
                fontWeight = FontWeight.Medium
              )
            },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = TextCrema,
              selectedTextColor = AccentTerracota,
              unselectedIconColor = TextMuted,
              unselectedTextColor = TextMuted,
              indicatorColor = AccentTerracota
            )
          )

          // Tab 1: Conocimiento
          NavigationBarItem(
            selected = currentTab == 1,
            onClick = { viewModel.setTab(1) },
            icon = {
              Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Conocimiento",
                modifier = Modifier.size(20.dp)
              )
            },
            label = {
              Text(
                "Conocimiento",
                fontSize = 10.sp,
                letterSpacing = 1.sp,
                fontWeight = FontWeight.Medium
              )
            },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = TextCrema,
              selectedTextColor = AccentTerracota,
              unselectedIconColor = TextMuted,
              unselectedTextColor = TextMuted,
              indicatorColor = AccentTerracota
            )
          )
        }
      }
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(BgDark)
        .padding(innerPadding)
    ) {
      AnimatedContent(
        targetState = currentTab,
        transitionSpec = {
          if (targetState > initialState) {
            slideInHorizontally(animationSpec = tween(400)) { width -> width } + fadeIn() with
                    slideOutHorizontally(animationSpec = tween(400)) { width -> -width } + fadeOut()
          } else {
            slideInHorizontally(animationSpec = tween(400)) { width -> -width } + fadeIn() with
                    slideOutHorizontally(animationSpec = tween(400)) { width -> width } + fadeOut()
          }
        },
        label = "TabContent"
      ) { tabIndex ->
        when (tabIndex) {
          0 -> ExpoMode(viewModel = viewModel)
          1 -> KnowledgeMode(viewModel = viewModel)
        }
      }
    }
  }
}
