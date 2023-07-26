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
            menuParlemen.add("Warta Parlemen")
            menuParlemen.add("Mutiara Parlemen")
            menuParlemen.add("PSA DPR RI")
            menuParlemen.add("Perempuan Parlemen")
            menuParlemen.add("KURMA DPR")
            menuParlemen.add("EPN")
            menuParlemen.add("Teras Parlemen")
            menuParlemen.add("Film DPR RI")
            menuParlemen.add("Bedah RUU")
            menuParlemen.add("Liputan Khusus")
            menuParlemen.add("Sidang Bersama DPR RI & DPD RI")
            menuParlemen.add("ID Station")
            menuParlemen.add("Serba - Serbi Parlemen")
            menuParlemen.add("Paripurna")
            menuParlemen.add("Mutiara Ramadhan")
            menuParlemen.add("Live Komisi")
            menuParlemen.add("Semangat Petang Parlemen")
            menuParlemen.add("Semangat Pagi Parlemen")
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