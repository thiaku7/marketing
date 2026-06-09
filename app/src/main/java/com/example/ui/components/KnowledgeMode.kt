package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.MarketingCard
import com.example.model.MarketingData
import com.example.ui.theme.*
import com.example.viewmodel.MarketingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KnowledgeMode(
    viewModel: MarketingViewModel,
    modifier: Modifier = Modifier
) {
    val favoriteIds by viewModel.favoriteIds.collectAsState()
    var selectedCategory by remember { mutableStateOf("all") }
    val listState = rememberLazyListState()

    // Smooth scroll to top when category changes
    LaunchedEffect(selectedCategory) {
        listState.animateScrollToItem(0)
    }

    // Filter categories: all sections info + custom favorites category
    val categories = remember {
        listOf(
            CategoryItem("all", "Todos"),
            CategoryItem("definicion", "01 — Definición"),
            CategoryItem("4p", "02 — Las 4P"),
            CategoryItem("segmentacion", "03 — Segmentación"),
            CategoryItem("digital", "04 — M. Digital"),
            CategoryItem("foda", "05 — FODA y TOWS"),
            CategoryItem("neuro", "06 — Neurociencia"),
            CategoryItem("favorites", "♥ Guardados")
        )
    }

    val cardsList = remember { MarketingData.cards }

    // Computed lists
    val filteredCards = remember(selectedCategory, favoriteIds) {
        when (selectedCategory) {
            "all" -> cardsList
            "favorites" -> cardsList.filter { favoriteIds.contains(it.id) }
            else -> cardsList.filter { it.section == selectedCategory }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgDark)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Hero Heading inside tab
        Text(
            text = "BASE DE CONOCIMIENTO",
            color = AccentTerracota,
            fontSize = 11.sp,
            letterSpacing = 3.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Text(
            text = "Repaso Post-Exposición",
            fontSize = 24.sp,
            fontFamily = FontFamily.Serif,
            color = TextCrema,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 2.dp)
        )
        Text(
            text = "Explora material detallado, tablas analíticas e hitos guardándolos en favoritos.",
            color = TextMuted,
            fontSize = 13.sp,
            lineHeight = 18.sp,
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 12.dp)
        )

        // Horizontal Category Tab Selector Bar
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { cat ->
                val isActive = selectedCategory == cat.id
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(if (isActive) AccentTerracota else CardBg)
                        .border(
                            width = 1.dp,
                            color = if (isActive) AccentTerracota else BorderDark,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .clickable { selectedCategory = cat.id }
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = cat.label,
                        color = if (isActive) TextCrema else TextSoft,
                        fontSize = 11.sp,
                        fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
                        letterSpacing = 1.sp
                    )
                }
            }
        }

        Divider(color = BorderDark, thickness = 1.dp)

        // Stream of contents
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // RENDER CUSTOM REVELATION MODULES AT THE TOP ONCE SELECTED
            if (selectedCategory == "all" || selectedCategory == "definicion") {
                item {
                    SectionHeader(
                        tag = "01 — Definición",
                        title = "El Misterio de la Elección",
                        desc = "El marketing no nació para vender. Nació para responder una pregunta más profunda: ¿por qué la gente elige?"
                    )
                }
                item {
                    QuoteBox(
                        quote = "\"El objetivo del marketing es conocer tan bien al cliente que el producto se venda solo.\"",
                        author = "Peter Drucker"
                    )
                }
                item {
                    TimelineModule()
                }
            }

            if (selectedCategory == "all" || selectedCategory == "4p") {
                item {
                    SectionHeader(
                        tag = "02 — Las 4P",
                        title = "El Sistema de Tensiones: 4P vs 4C",
                        desc = "No son una lista. Son un ecosistema donde si falla una pieza, el sistema entero colapsa."
                    )
                }
            }

            if (selectedCategory == "all" || selectedCategory == "segmentacion") {
                item {
                    SectionHeader(
                        tag = "03 — Segmentación",
                        title = "El Límite de la Segmentación",
                        desc = "No existe el cliente. Existe una constelación de perfiles humanos con necesidades radicalmente distintas."
                    )
                }
                item {
                    SegmentationTable()
                }
            }

            if (selectedCategory == "all" || selectedCategory == "digital") {
                item {
                    SectionHeader(
                        tag = "04 — Marketing Digital",
                        title = "La Revolución de la Métrica",
                        desc = "La revolución no fue internet. Fue la métrica. Por primera vez en la historia, una marca puede saber exactamente qué funcionó."
                    )
                }
                item {
                    DigitalComparisonTable()
                }
            }

            if (selectedCategory == "all" || selectedCategory == "foda") {
                item {
                    SectionHeader(
                        tag = "05 — FODA y TOWS",
                        title = "La Ilusión del Control",
                        desc = "El FODA no es un trámite de papelería. Es un ejercicio de brutal honestidad organizacional. Desarrollado por Albert Humphrey, Stanford, 1960s."
                    )
                }
                item {
                    TowsMatrixModule()
                }
            }

            if (selectedCategory == "all" || selectedCategory == "neuro") {
                item {
                    SectionHeader(
                        tag = "06 — Neurociencia",
                        title = "Por Qué Compramos Lo Que No Necesitamos",
                        desc = "El neuromarketing estudia cómo el cerebro responde a los estímulos antes de que la persona sea consciente de esa respuesta o impulso."
                    )
                }
            }

            // Empty state for saves if filtering by favorites
            if (selectedCategory == "favorites" && filteredCards.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 60.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "♥",
                            color = PrimaryDeep,
                            fontSize = 44.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Biblioteca de Favoritos vacía",
                            color = TextCrema,
                            fontSize = 16.sp,
                            fontFamily = FontFamily.Serif,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Busca tarjetas interesantes y toca el corazón para guardarlas aquí para futuros repasos.",
                            color = TextMuted,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 24.dp)
                        )
                    }
                }
            } else {
                // Regular Stream Cards
                items(filteredCards, key = { it.id }) { card ->
                    val isFav = favoriteIds.contains(card.id)
                    MarketingCardItem(
                        card = card,
                        isFavorite = isFav,
                        onFavoriteToggle = { viewModel.toggleFavorite(card.id) }
                    )
                }
            }

            // Bibliography item at bottom
            item {
                BibliographyModule()
            }
        }
    }
}

data class CategoryItem(val id: String, val label: String)

@Composable
fun SectionHeader(tag: String, title: String, desc: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 8.dp)
    ) {
        Text(
            text = tag,
            color = AccentTerracota,
            fontSize = 11.sp,
            letterSpacing = 4.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            fontSize = 22.sp,
            fontFamily = FontFamily.Serif,
            color = TextCrema
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = desc,
            color = TextSoft,
            fontSize = 13.sp,
            lineHeight = 19.sp
        )
    }
}

@Composable
fun QuoteBox(quote: String, author: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(1.dp, BorderDark), RoundedCornerShape(4.dp))
            .background(CardBg)
            .padding(20.dp)
    ) {
        Text(
            text = quote,
            fontSize = 16.sp,
            color = CodeStyleGold,
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily.Serif,
            lineHeight = 22.sp
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "— $author",
            color = TextMuted,
            fontSize = 11.sp,
            letterSpacing = 2.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun MarketingCardItem(
    card: MarketingCard,
    isFavorite: Boolean,
    onFavoriteToggle: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, BorderDark),
        colors = CardDefaults.cardColors(containerColor = CardBg)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Top Row (Category Tag + Favorite Button)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = card.category.uppercase(),
                    color = AccentTerracota,
                    fontSize = 10.sp,
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = onFavoriteToggle,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorito",
                        tint = if (isFavorite) AccentTerracota else TextMuted,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Title
            Text(
                text = card.title,
                fontSize = 18.sp,
                fontFamily = FontFamily.Serif,
                color = TextCrema
            )

            // Subtitle
            if (card.subtitle.isNotEmpty()) {
                Text(
                    text = card.subtitle,
                    fontSize = 12.sp,
                    color = TextMuted,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Body Description
            Text(
                text = card.description,
                color = TextSoft,
                fontSize = 13.sp,
                lineHeight = 19.sp
            )

            // Dynamic Accent block below
            if (card.accent.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = card.accent,
                    color = AccentTerracota,
                    fontStyle = FontStyle.Italic,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
fun TimelineModule() {
    val timelineEvents = listOf(
        Pair("1900 — Era de la Producción", Pair("Fabricar era suficiente", "Había escasez en el mercado. Quien producía, vendía. El marketing no existía porque no hacía falta.")),
        Pair("1950 — Era de las Ventas", Pair("Había que convencer al cliente", "Apareció la competencia agresiva. Las empresas empezaron a necesitar persuadir activamente al cliente.")),
        Pair("1990 — Era de la Relación", Pair("Fidelizar valía más que conquistar", "Mantener un cliente existente cuesta 5 veces menos que conseguir uno totalmente nuevo.")),
        Pair("Hoy — Era del Valor", Pair("El cliente elige lo que percibe", "AMA 2017: El marketing es la actividad de crear, comunicar y entregar valor. No objetos. Valor."))
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, BorderDark, RoundedCornerShape(4.dp))
            .background(CardBg)
            .padding(20.dp)
    ) {
        Text(
            text = "LÍNEA DE TIEMPO DEL MARKETING",
            color = PrimaryDeep,
            fontSize = 10.sp,
            letterSpacing = 2.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        timelineEvents.forEachIndexed { index, event ->
            Row(modifier = Modifier.fillMaxWidth()) {
                // Vertical connector strip
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(20.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(RoundedCornerShape(50))
                            .background(AccentTerracota)
                    )
                    if (index < timelineEvents.size - 1) {
                        Box(
                            modifier = Modifier
                                .width(1.5.dp)
                                .height(72.dp)
                                .background(BorderDark)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                // Item Text details
                Column(modifier = Modifier.padding(bottom = 16.dp)) {
                    Text(
                        text = event.first,
                        color = AccentTerracota,
                        fontSize = 11.sp,
                        letterSpacing = 1.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = event.second.first,
                        color = TextCrema,
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Serif
                    )
                    Text(
                        text = event.second.second,
                        color = TextSoft,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun SegmentationTable() {
    val tableRows = listOf(
        Triple("Demográfica", "Edad, género, ingresos, educación", "Coca-Cola Zero apunta a hombres jóvenes preocupados por la salud"),
        Triple("Geográfica", "País, ciudad, clima, región", "McDonald's adapta su menú a cada país: McAloo en India, Teriyaki en Japón"),
        Triple("Psicográfica", "Valores, estilo de vida, personalidad", "Nike no vende zapatillas. Vende a personas que se identifican como atletas"),
        Triple("Conductual", "Frecuencia de compra, lealtad, uso", "Amazon Prime premia la frecuencia con beneficios exclusivos")
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, BorderDark, RoundedCornerShape(4.dp))
            .background(CardBg)
            .padding(16.dp)
    ) {
        Text(
            text = "MATRIZ DE SEGMENTACIÓN COMPLETA",
            color = PrimaryDeep,
            fontSize = 10.sp,
            letterSpacing = 2.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        
        tableRows.forEach { row ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(0.5.dp, BorderDark)
                    .padding(12.dp)
            ) {
                Text(text = row.first, color = TextCrema, fontSize = 14.sp, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
                Text(text = "Variables: ${row.second}", color = TextMuted, fontSize = 11.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Ejemplo: ${row.third}", color = TextSoft, fontSize = 12.sp, fontStyle = FontStyle.Italic)
            }
        }
    }
}

@Composable
fun DigitalComparisonTable() {
    val compRows = listOf(
        Triple("Alcance", "Hablar a millones esperando llegar a algunos", "Hablar a uno esperando que se convierta en millones"),
        Triple("Costo", "Alto e impredecible", "Escalable y medible desde el primer peso"),
        Triple("Medición", "Puramente estimada (ratings, etc.)", "Tiempo real: clics, conversiones, retención"),
        Triple("Velocidad", "Semanas o meses para pivotar", "Horas o minutos para cambiar campaña"),
        Triple("Disciplina", "Arte mayormente ciega", "Ciencia de datos + Creatividad")
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, BorderDark, RoundedCornerShape(4.dp))
            .background(CardBg)
            .padding(16.dp)
    ) {
        Text(
            text = "COMPARATIVA: S.XX VS S.XXI",
            color = PrimaryDeep,
            fontSize = 10.sp,
            letterSpacing = 2.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        compRows.forEach { row ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(0.5.dp, BorderDark)
                    .padding(10.dp)
            ) {
                Text(
                    text = "DIMENSIÓN: " + row.first.uppercase(),
                    color = AccentTerracota,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Masivo S.XX", color = TextMuted, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        Text(text = row.second, color = TextSoft, fontSize = 11.sp, lineHeight = 15.sp)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Digital S.XXI", color = AccentTerracota.copy(alpha = 0.8f), fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        Text(text = row.third, color = TextCrema, fontSize = 11.sp, lineHeight = 15.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun TowsMatrixModule() {
    val towsRows = listOf(
        Pair("Fortalezas (F) con Oportunidades", "FO — Ofensiva: Usar fortalezas internas para capturar oportunidades. La más poderosa de las formulaciones."),
        Pair("Fortalezas (F) con Amenazas", "FA — Defensiva: Usar las fortalezas de la firma para neutralizar amenazas."),
        Pair("Debilidades (D) con Oportunidades", "DO — Mejora: Superar las debilidades operativas aprovechando oportunidades."),
        Pair("Debilidades (D) con Amenazas", "DA — Supervivencia: Minimizar debilidades y evitar amenazas directas. La más conservadora.")
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, BorderDark, RoundedCornerShape(4.dp))
            .background(CardBg)
            .padding(16.dp)
    ) {
        Text(
            text = "MATRIZ TOWS (ACCIONES EN COMBINACIÓN)",
            color = PrimaryDeep,
            fontSize = 10.sp,
            letterSpacing = 2.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        towsRows.forEach { row ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(0.5.dp, BorderDark)
                    .padding(10.dp)
            ) {
                Text(text = row.first, color = AccentTerracota, fontSize = 11.sp, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = row.second, color = TextSoft, fontSize = 12.sp, lineHeight = 17.sp)
            }
        }
    }
}

@Composable
fun BibliographyModule() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, BorderDark, RoundedCornerShape(4.dp))
            .background(CardBg)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "FUENTES ACADÉMICAS",
            color = PrimaryDeep,
            fontSize = 10.sp,
            letterSpacing = 3.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Text(
            text = "Kotler: Marketing Management, 15° ed, 2016\nMcCarthy: Basic Marketing, 1960\nLauterborn: New Marketing Litany, 1990\nHumphrey: SWOT, Stanford, 1960s\nZaltman: How Customers Think, Harvard, 2003\nAmerican Marketing Association Guidelines, 2017",
            color = TextMuted,
            fontSize = 11.sp,
            lineHeight = 18.sp,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Serif
        )
        Spacer(modifier = Modifier.height(14.dp))
        Divider(color = BorderDark, thickness = 1.dp)
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "marketing — la arquitectura invisible del deseo · 2026",
            color = TextMuted.copy(alpha = 0.5f),
            fontSize = 9.sp,
            letterSpacing = 1.5.sp
        )
    }
}
