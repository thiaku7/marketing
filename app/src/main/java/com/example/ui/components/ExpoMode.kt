package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.viewmodel.MarketingViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExpoMode(
    viewModel: MarketingViewModel,
    modifier: Modifier = Modifier
) {
    val currentStep by viewModel.currentExpoStep.collectAsState()
    val scrollState = rememberScrollState()

    // Reset scroll when step changes
    LaunchedEffect(currentStep) {
        scrollState.scrollTo(0)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgDark)
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        
        // Progress bar indicator
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "EXPERIENCIA INTERACTIVA",
                color = AccentTerracota,
                fontSize = 11.sp,
                letterSpacing = 2.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "0$currentStep / 06",
                color = TextMuted,
                fontSize = 11.sp,
                letterSpacing = 1.sp
            )
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // 6 segments progress bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            for (i in 1..6) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(2.dp)
                        .background(
                            if (i <= currentStep) AccentTerracota else BorderDark
                        )
                )
            }
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        // Step container
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            AnimatedContent(
                targetState = currentStep,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInHorizontally(animationSpec = tween(350)) { width -> width } + fadeIn() with
                                slideOutHorizontally(animationSpec = tween(350)) { width -> -width } + fadeOut()
                    } else {
                        slideInHorizontally(animationSpec = tween(350)) { width -> -width } + fadeIn() with
                                slideOutHorizontally(animationSpec = tween(350)) { width -> width } + fadeOut()
                    }
                },
                label = "StepContent"
            ) { targetStep ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                ) {
                    when (targetStep) {
                        1 -> Step1Question(viewModel)
                        2 -> Step2BrainBlocks()
                        3 -> Step3CocaColaCase()
                        4 -> Step4PAsSystem(viewModel)
                        5 -> Step5InteractiveSWOT(viewModel)
                        6 -> Step6CenitConclusion(viewModel)
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }

        // Bottom action controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (currentStep > 1) {
                OutlinedButton(
                    onClick = { viewModel.prevExpoStep() },
                    border = BorderStroke(1.dp, BorderDark),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextCrema),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Atrás",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Atrás", fontSize = 12.sp, letterSpacing = 1.sp)
                }
            } else {
                // Invisible placeholder to keep alignment
                Box(modifier = Modifier.size(1.dp))
            }

            IconButton(
                onClick = { viewModel.resetExpo() },
                modifier = Modifier.border(1.dp, BorderDark, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Reiniciar",
                    tint = TextMuted,
                    modifier = Modifier.size(18.dp)
                )
            }

            if (currentStep < 6) {
                val step1Answer by viewModel.selectedStep1Answer.collectAsState()
                // Prevent going to step 2 without answering on step 1
                val canProceed = currentStep != 1 || step1Answer != null
                
                Button(
                    onClick = { viewModel.nextExpoStep() },
                    enabled = canProceed,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AccentTerracota,
                        contentColor = TextCrema,
                        disabledContainerColor = BorderDark,
                        disabledContentColor = TextMuted
                    ),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text("Siguiente", fontSize = 12.sp, letterSpacing = 1.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Continuar",
                        modifier = Modifier.size(16.dp)
                    )
                }
            } else {
                Box(modifier = Modifier.size(1.dp))
            }
        }
    }
}

@Composable
fun Step1Question(viewModel: MarketingViewModel) {
    val selectedAnswer by viewModel.selectedStep1Answer.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "01 — La Gran Premisa",
            color = PrimaryDeep,
            fontSize = 11.sp,
            letterSpacing = 3.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "¿Alguna vez compraste algo que no necesitabas?",
            fontSize = 28.sp,
            fontFamily = FontFamily.Serif,
            color = TextCrema,
            textAlign = TextAlign.Center,
            lineHeight = 36.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        
        Spacer(modifier = Modifier.height(36.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // SÍ BUTTON
            val yesSelected = selectedAnswer == "SI"
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(4.dp))
                    .background(if (yesSelected) AccentTerracota else CardBg)
                    .border(
                        1.dp,
                        if (yesSelected) AccentTerracota else BorderDark,
                        RoundedCornerShape(4.dp)
                    )
                    .clickable { viewModel.setStep1Answer("SI") }
                    .padding(vertical = 18.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Sí, claro",
                    color = if (yesSelected) TextCrema else TextCrema,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Serif
                )
            }

            // NO BUTTON
            val noSelected = selectedAnswer == "NO"
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(4.dp))
                    .background(if (noSelected) AccentTerracota else CardBg)
                    .border(
                        1.dp,
                        if (noSelected) AccentTerracota else BorderDark,
                        RoundedCornerShape(4.dp)
                    )
                    .clickable { viewModel.setStep1Answer("NO") }
                    .padding(vertical = 18.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No, nunca",
                    color = if (noSelected) TextCrema else TextCrema,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Serif
                )
            }
        }
        
        Spacer(modifier = Modifier.height(40.dp))
        
        AnimatedVisibility(
            visible = selectedAnswer != null,
            enter = fadeIn(animationSpec = tween(500)) + expandVertically(),
            exit = fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BorderDark, RoundedCornerShape(4.dp))
                    .background(CardBg)
                    .padding(20.dp)
            ) {
                Text(
                    text = if (selectedAnswer == "SI") "RESPUESTA: EFECTO IMPULSIVO" else "RESPUESTA: ILUSIÓN DE LA RAZÓN",
                    color = AccentTerracota,
                    fontSize = 10.sp,
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                
                Text(
                    text = if (selectedAnswer == "SI") {
                        "¡Exacto! El cerebro te convence de que lo necesitas para justificar un impulso puramente emocional. El 95% de nuestras elecciones ocurren antes de que seamos capaces de verbalizarlas."
                    } else {
                        "¿Estás seguro? La ciencia neurológica demuestra que el 95% de las compras se realizan de manera inconsciente. Tal vez fuiste persuadido silenciosamente por un ecosistema de valor que tu cerebro racional simplemente asimiló como propio."
                    },
                    color = TextSoft,
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    fontFamily = FontFamily.Default
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Ambas respuestas nos conducen a la arquitectura biológica de la decisión...",
                    color = TextMuted,
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}

@Composable
fun Step2BrainBlocks() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "02 — Arquitectura del Cerebro",
            color = PrimaryDeep,
            fontSize = 11.sp,
            letterSpacing = 3.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "La Proporción Invisible",
            fontSize = 24.sp,
            fontFamily = FontFamily.Serif,
            color = TextCrema
        )
        Text(
            text = "Dos bloques de color que expresan cómo se divide realmente el poder de elección humana.",
            color = TextMuted,
            fontSize = 13.sp,
            lineHeight = 18.sp
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Visual Proportion Blocks
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, BorderDark, RoundedCornerShape(4.dp))
                .clip(RoundedCornerShape(4.dp))
        ) {
            // RATIONAL BRAIN: 5% (Small block)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(RationalBlue)
                    .padding(vertical = 12.dp, horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "CEREBRO RACIONAL — Prefrontal",
                        color = TextCrema,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "5%",
                        color = TextCrema,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )
                }
            }

            // EMOTIONAL BRAIN: 95% (Huge block)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AccentTerracota)
                    .padding(top = 40.dp, bottom = 40.dp, start = 16.dp, end = 16.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "CEREBRO EMOCIONAL — Sistema Límbico",
                            color = TextCrema,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "95%",
                            color = TextCrema,
                            fontSize = 44.sp,
                            fontWeight = FontWeight.Light,
                            fontFamily = FontFamily.Serif
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Éste es el centro donde se toman realmente las decisiones de compra. Es espontáneo, emocional, instintivo y totalmente inconsciente. No responde a la lógica: responde a símbolos, miedos, estatus, conexión y deseos profundos.",
                        color = TextCrema.copy(alpha = 0.9f),
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Rational detail note below
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, BorderDark, RoundedCornerShape(4.dp))
                .background(CardBg)
                .padding(16.dp)
        ) {
            Text(
                text = "EL RACIONAL LLEGA TARDE...",
                color = RationalBlue,
                fontSize = 10.sp,
                letterSpacing = 1.5.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "La corteza prefrontal (5%) apenas actúa como un narrador externo. Su única función real es inventar razones artificiales lógicas para convencerte de que tu compra impulsiva (decidida por el límbico 95%) fue sumamente astuta e inteligente.",
                color = TextSoft,
                fontSize = 13.sp,
                lineHeight = 19.sp
            )
        }
    }
}

@Composable
fun Step3CocaColaCase() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "03 — Experimentos de Poder",
            color = PrimaryDeep,
            fontSize = 11.sp,
            letterSpacing = 3.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "La Paradoja de Zaltman",
            fontSize = 24.sp,
            fontFamily = FontFamily.Serif,
            color = TextCrema
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Case 1: Coca Cola
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(1.dp, BorderDark),
            colors = CardDefaults.cardColors(containerColor = CardBg)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "CASO COCA-COLA",
                    color = AccentTerracota,
                    fontSize = 10.sp,
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "El Refresco Invisible",
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Serif,
                    color = TextCrema
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "En pruebas ciegas de sabor hechas en escáneres cerebrales, las personas elegían Pepsi de manera abrumadora debido al sabor físico.\n\nSin embargo, cuando veían la marca Coca-Cola, las personas de pronto cambiaban su elección química: se activaba su lóbulo frontal. Tu cerebro no eligió sabor gaseoso; eligió el estatus, el recuerdo familiar y la identidad simbólica de Coca-Cola.",
                    color = TextSoft,
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                )
            }
        }

        // Case 2: Zaltman
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(1.dp, BorderDark),
            colors = CardDefaults.cardColors(containerColor = CardBg)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "LA PARADOJA DE ZALTMAN",
                    color = PrimaryDeep,
                    fontSize = 10.sp,
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "\"La gente no puede decir lo que piensa...\"",
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Serif,
                    color = TextCrema,
                    fontStyle = FontStyle.Italic
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "El renombrado académico de Harvard, Gerald Zaltman, concluye que los consumidores sencillamente no piensan de manera lógica ni lineal. Por lo tanto, no pueden verbalizar fielmente por qué eligen un producto.\n\nInvestigar mercados mediante tradicionales encuestas racionales es consultarle al cerebro equivocado (el 5%). El verdadero jefe (el 95%) no habla con palabras, habla con emociones.",
                    color = TextSoft,
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Composable
fun Step4PAsSystem(viewModel: MarketingViewModel) {
    val activeP by viewModel.activeP4.collectAsState()

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "04 — La Estructura Holística",
            color = PrimaryDeep,
            fontSize = 11.sp,
            letterSpacing = 3.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Las 4P como Sistema",
            fontSize = 24.sp,
            fontFamily = FontFamily.Serif,
            color = TextCrema
        )
        Text(
            text = "Toca cada nodo para ver cómo se conecta en el ecosistema 4P vs 4C.",
            color = TextMuted,
            fontSize = 13.sp,
            lineHeight = 18.sp
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Interconnected circles layout
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .border(1.dp, BorderDark, RoundedCornerShape(4.dp))
                .background(CardBg),
            contentAlignment = Alignment.Center
        ) {
            // Draw connecting links in background
            Box(
                modifier = Modifier
                    .width(180.dp)
                    .height(2.dp)
                    .background(AccentTerracota.copy(alpha = 0.5f))
            )
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(120.dp)
                    .background(AccentTerracota.copy(alpha = 0.5f))
            )

            // Circles layout
            Box(modifier = Modifier.fillMaxSize()) {
                // PRODUCTO - Top
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 16.dp)
                ) {
                    CircleNode(
                        label = "P1",
                        name = "Producto",
                        isActive = activeP == "P1",
                        onClick = { viewModel.setActiveP4("P1") }
                    )
                }

                // PLAZA - Left
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 32.dp)
                ) {
                    CircleNode(
                        label = "P3",
                        name = "Plaza",
                        isActive = activeP == "P3",
                        onClick = { viewModel.setActiveP4("P3") }
                    )
                }

                // PRECIO - Right
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 32.dp)
                ) {
                    CircleNode(
                        label = "P2",
                        name = "Precio",
                        isActive = activeP == "P2",
                        onClick = { viewModel.setActiveP4("P2") }
                    )
                }

                // PROMOCIÓN - Bottom
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                ) {
                    CircleNode(
                        label = "P4",
                        name = "Promoción",
                        isActive = activeP == "P4",
                        onClick = { viewModel.setActiveP4("P4") }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Info of selected P
        AnimatedContent(
            targetState = activeP,
            label = "PContent"
        ) { pId ->
            val pContent = when (pId) {
                "P1" -> Triple(
                    "P1 — PRODUCTO",
                    "La Solución de Identidad",
                    "No es un objeto físico. Es la identidad simbólica que representa.\n\nApple no vende terminales móviles: vende estatus, pertenencia y simpleza de diseño.\n\n→ Equivale a C1: Consumer. ¿Qué problema humano real resuelve tu producto?"
                )
                "P2" -> Triple(
                    "P2 — PRECIO",
                    "El Mensaje de Posicionamiento",
                    "No es un número frío de costo + margen. Es una declaración de valor percibido.\n\nUn precio extrañamente bajo arruina la credibilidad. Un precio premium genera misterio y enorme deseo.\n\n→ Equivale a C2: Cost. ¿Cuánto le cuesta al cliente en su recurso más escaso: tiempo y esfuerzo?"
                )
                "P3" -> Triple(
                    "P3 — PLAZA",
                    "El Acceso Inmediato",
                    "Dónde y cómo el producto encuentra las manos del cliente. Hoy la plaza ya no es un estante; es un algoritmo de recomendación de Instagram o un dron volando en 30 minutos.\n\n→ Equivale a C3: Convenience. ¿Qué tan fácil y placentera es la logística de acceso?"
                )
                else -> Triple(
                    "P4 — PROMOCIÓN",
                    "La Precisión Científica",
                    "No se trata de gritar más fuerte (anuncios molestos}. Se trata de hablar directo y con extrema precisión.\n\nEl mensaje correcto, a la persona correcta, en el segundo perfecto.\n\n→ Equivale a C4: Communication. ¿Existe un diálogo interactivo y recíproco con tu audiencia?"
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, AccentTerracota, RoundedCornerShape(4.dp))
                    .background(CardBg)
                    .padding(20.dp)
            ) {
                Text(
                    text = pContent.first,
                    color = AccentTerracota,
                    fontSize = 11.sp,
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = pContent.second,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Serif,
                    color = TextCrema
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = pContent.third,
                    color = TextSoft,
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Composable
fun CircleNode(
    label: String,
    name: String,
    isActive: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(if (isActive) AccentTerracota else BgDark)
                .border(
                    width = 1.5.dp,
                    color = if (isActive) AccentTerracota else TextMuted,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                color = if (isActive) TextCrema else TextMuted,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = name,
            color = if (isActive) TextCrema else TextMuted,
            fontSize = 10.sp,
            letterSpacing = 0.5.sp
        )
    }
}

@Composable
fun Step5InteractiveSWOT(viewModel: MarketingViewModel) {
    val activeCell by viewModel.activeFodaCell.collectAsState()

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "05 — El Análisis Estratégico",
            color = PrimaryDeep,
            fontSize = 11.sp,
            letterSpacing = 3.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Cuadrícula FODA 2x2",
            fontSize = 24.sp,
            fontFamily = FontFamily.Serif,
            color = TextCrema
        )
        Text(
            text = "Toca cada cuadrante para expandir su definición y filosofía estratégica.",
            color = TextMuted,
            fontSize = 13.sp,
            lineHeight = 18.sp
        )
        Spacer(modifier = Modifier.height(24.dp))

        // 2x2 Grid
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, BorderDark, RoundedCornerShape(4.dp))
                .clip(RoundedCornerShape(4.dp))
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                // Fortalezas (Top Left)
                FodaGridCell(
                    label = "F",
                    title = "Fortalezas",
                    sub = "Interno Positivo",
                    colorTheme = SuccessDark,
                    isActive = activeCell == "F",
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.setActiveFodaCell(if (activeCell == "F") null else "F") }
                )
                // Debilidades (Top Right)
                FodaGridCell(
                    label = "D",
                    title = "Debilidades",
                    sub = "Interno Negativo",
                    colorTheme = AccentTerracota,
                    isActive = activeCell == "D",
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.setActiveFodaCell(if (activeCell == "D") null else "D") }
                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                // Oportunidades (Bottom Left)
                FodaGridCell(
                    label = "O",
                    title = "Oportunidades",
                    sub = "Externo Positivo",
                    colorTheme = SuccessDark,
                    isActive = activeCell == "O",
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.setActiveFodaCell(if (activeCell == "O") null else "O") }
                )
                // Amenazas (Bottom Right)
                FodaGridCell(
                    label = "A",
                    title = "Amenazas",
                    sub = "Externo Negativo",
                    colorTheme = AccentTerracota,
                    isActive = activeCell == "A",
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.setActiveFodaCell(if (activeCell == "A") null else "A") }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Expand detail of selected Cell
        AnimatedVisibility(
            visible = activeCell != null,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            activeCell?.let { cell ->
                val (title, philosophy, fullDesc) = when (cell) {
                    "F" -> Triple(
                        "Fortalezas (F)",
                        "Los pilares que ya posees.",
                        "Consisten en recursos de poder que te otorgan ventajas reales competitivas frente al mercado.\nNo se refiere a lo que la firma considera internamente que hace bien, sino a los atributos objetivos tangibles o intangibles que tu público final reconoce de ti y valora activamente."
                    )
                    "D" -> Triple(
                        "Debilidades (D)",
                        "El talón de Aquiles estratégico.",
                        "Son factores internos críticos que sabotean o aminoran tu rendimiento estratégico frente a otros competidores.\nIgnorarlas deliberadamente es un boleto directo a la quiebra de la organización. Recuerda: una debilidad ignorada hoy es una grave amenaza mañana."
                    )
                    "O" -> Triple(
                        "Oportunidades (O)",
                        "Las olas de la tendencia del mañana y su velocidad.",
                        "Factores externos del contexto, legales, demográficos, tecnológicos o políticos, que son potencialmente aprovechables si se actúa veloz y decididamente antes que colapsen.\nEl mercado jamás permanece inmóvil. El que no se reconfigura para agarrar la ola, perece en el olvido."
                    )
                    else -> Triple(
                        "Amenazas (A)",
                        "La marea destructora imprevista.",
                        "Tendencias externas negativas del mercado político, comercial u organizativo que pueden perjudicar la propia existencia de la empresa.\nEl caso icónico Kodak: identificó plenamente la fotografía digital como una amenaza técnica desde 1975, pero estimó que sería más prudente ignorarla para proteger el film físico. Quebró rotundamente en 2012."
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, BorderDark, RoundedCornerShape(4.dp))
                        .background(CardBg)
                        .padding(20.dp)
                ) {
                    Text(
                        text = "DETALLE DE MATRIZ",
                        color = AccentTerracota,
                        fontSize = 10.sp,
                        letterSpacing = 1.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Serif,
                        color = TextCrema
                    )
                    Text(
                        text = philosophy,
                        color = TextMuted,
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Italic
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = fullDesc,
                        color = TextSoft,
                        fontSize = 13.sp,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
fun FodaGridCell(
    label: String,
    title: String,
    sub: String,
    colorTheme: Color,
    isActive: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(if (isActive) BorderDark else CardBg)
            .border(0.5.dp, BorderDark)
            .clickable { onClick() }
            .padding(18.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(colorTheme),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = label,
                        color = TextCrema,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                if (isActive) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(AccentTerracota)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextCrema
            )
            Text(
                text = sub,
                fontSize = 10.sp,
                color = TextMuted,
                letterSpacing = 0.5.sp
            )
        }
    }
}

@Composable
fun Step6CenitConclusion(viewModel: MarketingViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "06 — CULMINACIÓN NATURAL",
            color = PrimaryDeep,
            fontSize = 11.sp,
            letterSpacing = 3.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        // CÉNIT Display Card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, AccentTerracota, RoundedCornerShape(4.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF16100F),
                            BgDark
                        )
                    )
                )
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "CÉNIT",
                color = AccentTerracota,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Light,
                fontSize = 44.sp,
                letterSpacing = 6.sp
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = "Una comunidad diseñada para las personas que ya saben que quieren más, pero necesitan el sistema para lograrlo.",
                color = TextSoft,
                fontSize = 14.sp,
                lineHeight = 22.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Default
            )
            
            Spacer(modifier = Modifier.height(28.dp))
            
            // Value tags
            val tagsList = listOf("Mente", "Organización", "Tecnología", "Educación Financiera", "Comunicación", "Legado")
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tagsList.forEach { tag ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                            .border(1.dp, BorderDark, RoundedCornerShape(4.dp))
                            .background(CardBg)
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = tag,
                            color = TextMuted,
                            fontSize = 10.sp,
                            letterSpacing = 1.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))
            Divider(color = BorderDark, thickness = 1.dp)
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "\"El marketing de CÉNIT no manipula mentes. Revela una necesidad que ya existía en vos.\"",
                color = CodeStyleGold,
                fontSize = 15.sp,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Serif,
                lineHeight = 22.sp
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Large Premium Finishing Button
        Button(
            onClick = {
                viewModel.resetExpo()
                viewModel.setTab(1) // Navigation shift to Knowledge Module
            },
            colors = ButtonDefaults.buttonColors(containerColor = AccentTerracota),
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = "ABRIR MODO CONOCIMIENTO", 
                fontSize = 13.sp, 
                letterSpacing = 2.sp, 
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable () -> Unit
) {
    androidx.compose.foundation.layout.FlowRow(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement
    ) {
        content()
    }
}
