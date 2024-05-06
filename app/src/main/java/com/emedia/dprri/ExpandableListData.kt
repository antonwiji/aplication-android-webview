import java.util.*
internal object ExpandableListData {
    val data: HashMap<String, List<String>>
        get() {
            val expandableListDetail = HashMap<String, List<String>>()
            val menuPublishing: MutableList<String> = ArrayList()
            menuPublishing.add("E-Book")
            menuPublishing.add("E-Magazine")
            menuPublishing.add("E-Buletin")
            val menuParlemen: MutableList<String> = ArrayList()
            menuParlemen.add("TVR PARLEMEN")
            val menuMedtaksos: MutableList<String> = ArrayList()
            menuMedtaksos.add("Instagram")
            menuMedtaksos.add("Facebook")
            menuMedtaksos.add("Twitter")
            menuMedtaksos.add("Tiktok")
            menuMedtaksos.add("Youtube")
            menuMedtaksos.add("Official Site")
            expandableListDetail["DIGITAL PUBLISHING"] = menuPublishing
            expandableListDetail["TVR PARLEMEN"] = menuParlemen
            expandableListDetail["MEDTAKSOS"] = menuMedtaksos
            return expandableListDetail
        }
}