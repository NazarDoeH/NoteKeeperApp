package com.lab5.backend

enum class Region(val uid: Int, val displayName: String) {
    KHMELNYTSKA(3, "Хмельницька область"),
    VINNYTSKA(4, "Вінницька область"),
    RIVNENSKA(5, "Рівненська область"),
    VOLYNSKA(8, "Волинська область"),
    DNIPROPETROVSKA(9, "Дніпропетровська область"),
    ZHYTOMYRSKA(10, "Житомирська область"),
    ZAKARPATSKA(11, "Закарпатська область"),
    ZAPORIZHZHSKA(12, "Запорізька область"),
    IVANO_FRANKIVSKA(13, "Івано-Франківська область"),
    KYIVSKA(14, "Київська область"),
    KIROVOHRADSKA(15, "Кіровоградська область"),
    LUHANSKA(16, "Луганська область"),
    MYKOLAIVSKA(17, "Миколаївська область"),
    ODESKA(18, "Одеська область"),
    POLTAVSKA(19, "Полтавська область"),
    SUMSKA(20, "Сумська область"),
    TERNOPILSKA(21, "Тернопільська область"),
    KHARKIVSKA(22, "Харківська область"),
    KHERSONSKA(23, "Херсонська область"),
    CHERKASKA(24, "Черкаська область"),
    CHERNIGIVSKA(25, "Чернігівська область"),
    CHERNIVETSKA(26, "Чернівецька область"),
    LVIVSKA(27, "Львівська область"),
    DONETSKA(28, "Донецька область"),
    CRIMEA(29, "Автономна Республіка Крим"),
    SEVASTOPOL(30, "м. Севастополь"),
    KYIV(31, "м. Київ");

    companion object {
        fun fromUid(uid: Int): String? {
            val region = values().find { it.uid == uid }
            return region?.displayName
        }
    }
}
