package com.example.model

data class MarketingCard(
    val id: String,
    val section: String, // "definicion", "4p", "segmentacion", "digital", "foda", "neuro"
    val category: String, // "Cita", "Época", "4P vs 4C", "Tipo", "Dato", "Matriz", "FODA"
    val title: String,
    val subtitle: String = "",
    val description: String,
    val accent: String = ""
)

object MarketingData {
    val sections = listOf(
        SectionInfo("definicion", "01 — Definición", "El Misterio de la Elección", "El marketing no nació para vender. Nació para responder una pregunta más profunda: ¿por qué la gente elige?"),
        SectionInfo("4p", "02 — Las 4P", "El Sistema de Tensiones: 4P vs 4C", "No son una lista. Son un ecosistema donde si falla una pieza, el sistema entero colapsa."),
        SectionInfo("segmentacion", "03 — Segmentación", "El Límite de la Segmentación", "No existe el cliente. Existe una constelación de perfiles humanos con necesidades radicalmente distintas."),
        SectionInfo("digital", "04 — Marketing Digital", "La Revolución de la Métrica", "La revolución no fue internet. Fue la métrica. Por primera vez en la historia, una marca puede saber exactamente qué funcionó."),
        SectionInfo("foda", "05 — FODA y TOWS", "La Ilusión del Control", "El FODA no es un trámite de papelería. Es un ejercicio de brutal honestidad organizacional. Desarrollado por Albert Humphrey, Stanford, 1960s."),
        SectionInfo("neuro", "06 — Neurociencia", "Por Qué Compramos Lo Que No Necesitamos", "El neuromarketing estudia cómo el cerebro responde a los estímulos antes de que la persona sea consciente de esa respuesta.")
    )

    data class SectionInfo(
        val id: String,
        val tag: String,
        val title: String,
        val intro: String
    )

    val cards = listOf(
        // DEFINICION
        MarketingCard(
            id = "def_quote_1",
            section = "definicion",
            category = "Cita",
            title = "Peter Drucker",
            description = "\"El objetivo del marketing es conocer tan bien al cliente que el producto se venda solo.\""
        ),
        MarketingCard(
            id = "def_era_1",
            section = "definicion",
            category = "Época (1900)",
            title = "Era de la Producción",
            subtitle = "Fabricar era suficiente",
            description = "Había escasez. Quien producía, vendía. El marketing no existía porque no hacía falta."
        ),
        MarketingCard(
            id = "def_era_2",
            section = "definicion",
            category = "Época (1950)",
            title = "Era de las Ventas",
            subtitle = "Había que convencer",
            description = "Apareció la competencia. Las empresas empezaron a necesitar persuadir al cliente."
        ),
        MarketingCard(
            id = "def_era_3",
            section = "definicion",
            category = "Época (1990)",
            title = "Era de la Relación",
            subtitle = "Fidelizar valía más que conquistar",
            description = "Mantener un cliente existente cuesta 5 veces menos que conseguir uno nuevo."
        ),
        MarketingCard(
            id = "def_era_4",
            section = "definicion",
            category = "Época (Hoy)",
            title = "Era del Valor",
            subtitle = "El cliente elige lo que percibe",
            description = "AMA 2017: El marketing es la actividad de crear, comunicar y entregar valor. No objetos. Valor."
        ),
        MarketingCard(
            id = "def_quote_2",
            section = "definicion",
            category = "Cita",
            title = "Philip Kotler",
            description = "\"El marketing no es el arte de vender lo que fabricas. Es la arquitectura de la empatía.\""
        ),

        // 4P
        MarketingCard(
            id = "4p_p1",
            section = "4p",
            category = "4P — Producto",
            title = "La Solución",
            subtitle = "P1 — Producto",
            description = "No es el objeto físico. Es la identidad que representa. Apple no vende teléfonos: vende estatus, pertenencia y simpleza.",
            accent = "→ C1: Consumer. ¿Qué necesita realmente el cliente?"
        ),
        MarketingCard(
            id = "4p_p2",
            section = "4p",
            category = "4P — Precio",
            title = "El Mensaje",
            subtitle = "P2 — Precio",
            description = "No es un número. Es una declaración de valor percibido. Un precio bajo destruye credibilidad. Un precio premium genera deseo.",
            accent = "→ C2: Cost. ¿Cuánto le cuesta al cliente en tiempo y esfuerzo?"
        ),
        MarketingCard(
            id = "4p_p3",
            section = "4p",
            category = "4P — Plaza",
            title = "El Acceso",
            subtitle = "P3 — Plaza",
            description = "Dónde y cómo el producto encuentra al cliente. Hoy la plaza puede ser un algoritmo de Instagram o un dron en 30 minutos.",
            accent = "→ C3: Convenience. ¿Qué tan fácil es para el cliente acceder?"
        ),
        MarketingCard(
            id = "4p_p4",
            section = "4p",
            category = "4P — Promoción",
            title = "La Precisión",
            subtitle = "P4 — Promoción",
            description = "No es gritar más fuerte. Es hablar más preciso. El mensaje correcto, a la persona correcta, en el momento correcto.",
            accent = "→ C4: Communication. ¿Hay un diálogo real con el cliente?"
        ),
        MarketingCard(
            id = "4p_quote_1",
            section = "4p",
            category = "Cita",
            title = "Robert Lauterborn, 1990",
            description = "\"Las 4P miran desde la conveniencia de la empresa. Las 4C miran desde el humano. Esta fricción nunca se resolvió.\""
        ),

        // SEGMENTACION
        MarketingCard(
            id = "seg_t1",
            section = "segmentacion",
            category = "Segmentación",
            title = "Demográfica",
            subtitle = "Variables: Edad, género, ingresos, educación",
            description = "Ejemplo Real: Coca-Cola Zero apunta a hombres jóvenes preocupados por la salud."
        ),
        MarketingCard(
            id = "seg_t2",
            section = "segmentacion",
            category = "Segmentación",
            title = "Geográfica",
            subtitle = "Variables: País, ciudad, clima, región",
            description = "Ejemplo Real: McDonald's adapta su menú a cada país: McAloo en India, Teriyaki en Japón."
        ),
        MarketingCard(
            id = "seg_t3",
            section = "segmentacion",
            category = "Segmentación",
            title = "Psicográfica",
            subtitle = "Variables: Valores, estilo de vida, personalidad",
            description = "Ejemplo Real: Nike no vende zapatillas. Vende a personas que se identifican como atletas."
        ),
        MarketingCard(
            id = "seg_t4",
            section = "segmentacion",
            category = "Segmentación",
            title = "Conductual",
            subtitle = "Variables: Frecuencia de compra, lealtad, uso",
            description = "Ejemplo Real: Amazon Prime premia la frecuencia con beneficios exclusivos."
        ),
        MarketingCard(
            id = "seg_quote_1",
            section = "segmentacion",
            category = "Cita",
            title = "La Paradoja de la Precisión",
            description = "\"Mientras más exacta es la segmentación, más se acerca al individuo. Al llegar al individuo, la segmentación se destruye a sí misma y se convierte en personalización.\""
        ),
        MarketingCard(
            id = "seg_data_1",
            section = "segmentacion",
            category = "Estadística — Coca-Cola",
            title = "500+ Marcas",
            description = "Porque reconocen que un mercado único no existe."
        ),
        MarketingCard(
            id = "seg_data_2",
            section = "segmentacion",
            category = "Estadística — Netflix",
            title = "1.300 Grupos",
            description = "Grupos de preferencias distintas para recomendar contenido adaptado a cada perfil."
        ),
        MarketingCard(
            id = "seg_data_3",
            section = "segmentacion",
            category = "Estadística Ética",
            title = "87M Perfiles — Cambridge Analytica",
            description = "Segmentación psicográfica para influir en comportamiento electoral. El límite ético del marketing."
        ),

        // DIGITAL
        MarketingCard(
            id = "dig_item_1",
            section = "digital",
            category = "Comparativa — Alcance",
            title = "Dimensión: Alcance",
            subtitle = "Tradicional vs Digital",
            description = "Masivo: Hablar a millones esperando llegar a algunos.\nDigital: Hablar a uno esperando que se convierta en millones."
        ),
        MarketingCard(
            id = "dig_item_2",
            section = "digital",
            category = "Comparativa — Costo",
            title = "Dimensión: Costo",
            subtitle = "Tradicional vs Digital",
            description = "Masivo: Alto e impredecible.\nDigital: Escalable y medible desde el primer peso."
        ),
        MarketingCard(
            id = "dig_item_3",
            section = "digital",
            category = "Comparativa — Medición",
            title = "Dimensión: Medición",
            subtitle = "Tradicional vs Digital",
            description = "Masivo: Puramente estimada (ratings, encuestas}.\nDigital: Tiempo real: clics, conversiones, retención exacta."
        ),
        MarketingCard(
            id = "dig_item_4",
            section = "digital",
            category = "Comparativa — Velocidad",
            title = "Dimensión: Velocidad",
            subtitle = "Tradicional vs Digital",
            description = "Masivo: Semanas o meses.\nDigital: Horas o minutos para cambiar una campaña."
        ),
        MarketingCard(
            id = "dig_item_5",
            section = "digital",
            category = "Comparativa — Disciplina",
            title = "Dimensión: Disciplina",
            subtitle = "Tradicional vs Digital",
            description = "Masivo: Arte ciega.\nDigital: Ciencia de datos + Creatividad."
        ),
        MarketingCard(
            id = "dig_quote_1",
            section = "digital",
            category = "Cita",
            title = "John Wanamaker",
            description = "\"Históricamente, el 50% del presupuesto publicitario se desperdiciaba. Pero nadie sabía cuál 50%.\""
        ),
        MarketingCard(
            id = "dig_data_1",
            section = "digital",
            category = "Dato Digital",
            title = "5.4B Usuarios (2024)",
            subtitle = "We Are Social",
            description = "El mercado digital conectado más grande de la historia humana."
        ),
        MarketingCard(
            id = "dig_data_2",
            section = "digital",
            category = "Dato de Compra",
            title = "63% Compras inician online",
            description = "Antes de ir a una tienda física, el cliente ya decidió examinando en internet."
        ),
        MarketingCard(
            id = "dig_data_3",
            section = "digital",
            category = "Inversión 2024",
            title = "$600B USD Publicidad",
            subtitle = "Statista",
            description = "Inversión global dedicada exclusivamente al ecosistema digital."
        ),

        // FODA
        MarketingCard(
            id = "foda_f",
            section = "foda",
            category = "FODA — Interno Positivo",
            title = "Fortalezas",
            description = "Recursos que generan ventaja real. No lo que la firma cree hacer bien, sino lo que el mercado reconoce.",
            accent = "La pandemia fue amenaza para muchos. Fue oportunidad para delivery y videollamadas."
        ),
        MarketingCard(
            id = "foda_d",
            section = "foda",
            category = "FODA — Interno Negativo",
            title = "Debilidades",
            description = "Limitaciones internas que sabotean el futuro. Ignorarlas es invitar a la bancarrota estratégica.",
            accent = "Una debilidad ignorada hoy es una amenaza mañana."
        ),
        MarketingCard(
            id = "foda_o",
            section = "foda",
            category = "FODA — Externo Positivo",
            title = "Oportunidades",
            description = "Tendencias externas aprovechables si se actúa a tiempo. El mercado cambia. Quien no ve el cambio, muere."
        ),
        MarketingCard(
            id = "foda_a",
            section = "foda",
            category = "FODA — Externo Negativo",
            title = "Amenazas",
            description = "Fuerzas externas destructivas. Kodak identificó la fotografía digital como amenaza en 1975. Eligió ignorarla. Quebró en 2012."
        ),
        MarketingCard(
            id = "foda_quote_1",
            section = "foda",
            category = "Cita",
            title = "Henry Mintzberg",
            description = "\"Las herramientas estáticas dan una falsa sensación de control. Fotografían un momento que ya cambió al terminar de analizarlo.\""
        ),
        MarketingCard(
            id = "tows_fo",
            section = "foda",
            category = "TOWS — Estrategia Ofensiva",
            title = "Estrategia FO",
            description = "Usar fortalezas para capturar oportunidades. Es la combinación de fuerzas más poderosa."
        ),
        MarketingCard(
            id = "tows_fa",
            section = "foda",
            category = "TOWS — Estrategia Defensiva",
            title = "Estrategia FA",
            description = "Usar fortalezas de la organización para neutralizar amenazas externas."
        ),
        MarketingCard(
            id = "tows_do",
            section = "foda",
            category = "TOWS — Estrategia de Mejora",
            title = "Estrategia DO",
            description = "Superar debilidades internas aprovechando oportunidades externas que se presentan."
        ),
        MarketingCard(
            id = "tows_da",
            section = "foda",
            category = "TOWS — Estrategia de Supervivencia",
            title = "Estrategia DA",
            description = "Minimizar debilidades internas y evitar amenazas. La estrategia más conservadora."
        ),

        // NEUROCIENCIA
        MarketingCard(
            id = "neuro_rational",
            section = "neuro",
            category = "Neurociencia — 5%",
            title = "Cerebro Racional",
            subtitle = "Corteza Prefrontal",
            description = "Llega tarde. Analiza, razona, justifica. Pero cuando actúa, el límbico ya decidió. Es el narrador de una historia que no escribió."
        ),
        MarketingCard(
            id = "neuro_emotional",
            section = "neuro",
            category = "Neurociencia — 95%",
            title = "Cerebro Emocional",
            subtitle = "Sistema Límbico",
            description = "Aquí se decide. Rápido, instintivo, emocional. No razona. Siente. Las marcas más poderosas del mundo le hablan a este cerebro."
        ),
        MarketingCard(
            id = "neuro_quote_1",
            section = "neuro",
            category = "Cita",
            title = "Gerald Zaltman, Harvard",
            description = "\"El 95% de las decisiones de compra son inconscientes. Esto destruye el modelo del consumidor racional que el marketing clásico asumía.\""
        ),
        MarketingCard(
            id = "neuro_case_coca",
            section = "neuro",
            category = "Caso de Estudio",
            title = "El Experimento Coca-Cola",
            description = "En pruebas ciegas de sabor, Pepsi ganaba. Cuando se mostraba la marca Coca-Cola, ganaba Coca-Cola. El cerebro no eligió el sabor. Eligió el significado emocional de la marca."
        ),
        MarketingCard(
            id = "neuro_data_bremen",
            section = "neuro",
            category = "Dato del Cerebro",
            title = "2.5 Segundos Antes",
            subtitle = "Universidad de Bremen",
            description = "El cerebro toma decisiones de compra 2.5 segundos antes de que la persona sea consciente de haberlas tomado."
        ),
        MarketingCard(
            id = "neuro_quote_2",
            section = "neuro",
            category = "Cita",
            title = "Neuromarketing",
            description = "\"No vendas al cerebro racional. Ese cerebro llega tarde a cada decisión.\""
        )
    )
}
