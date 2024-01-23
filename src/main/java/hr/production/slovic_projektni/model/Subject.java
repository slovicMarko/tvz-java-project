package hr.production.slovic_projektni.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Subject {

    ADMINISTRIRANJE_RACUNALNIH_MREZA("Administrianje računalnih mreža", "Dunja Bjelobrk Knežević"),
    ADMINISTRIRANJE_UNIX_SUSTAVA("Administrianje UNIX sustava", "Davor Cafuta"),
    ALGORITMI_I_STRUKTURE_PODATAKA("Algoritmi i strukture podataka", "Aleksandar Stojanović"),
    ARHITEKTURA_RACUNALA("Arhitektura računala", "Jelena Kapelac Gulić"),
    BAZE_PODATAKA("Baza podataka", "Tin Kramberger"),
    DIGITALNA_LOGIKA_I_SKLOPOVI("Digitalna logika i sklopovi", "Jelena Kapelac Gulić"),
    DUBOKO_UCENJE("Duboko učenje", "Tin Kramberger"),
    ENGLESKI_JEZIK_U_RACUNARSTVU_1("Engleski jezik u računarstvu 1", "Biljana Stojaković"),
    ENGLESKI_JEZIK_U_RACUNARSTVU_2("Engleski jezik u računarstvu 2", "Biljana Stojaković"),
    KINEZIOLOSKA_KULTURA_I("Kineziološka kultura I", "Marko Milanović"),
    KINEZIOLOSKA_KULTURA_II("Kineziološka kultura II", "Marko Milanović"),
    KINEZIOLOSKA_KULTURA_III("Kineziološka kultura III", "Marko Milanović"),
    KINEZIOLOSKA_KULTURA_IV("Kineziološka kultura IV", "Marko Milanović"),
    MATEMATIKA_I("Matematika I", "Tihana Strmečki"),
    MATEMATIKA_II("Matematika II", "Tihana Strmečki"),
    METODOLOGIJA_POSLOVNIH_PROCESA("Metodologija poslovnih procesa", "Brigitta Cafuta"),
    METODOLOGIJA_STRUCNOG_I_ISTRAZIVACKOG_RADA("Metodologija stručnog i istraživačkog rada", "Lidija Tepeš Golubić"),
    MREZNE_USLUGE("Mrežne usluge", "Ognjen Mitrović"),
    NAPREDNE_BAZE_PODATAKA("Napredne baze podataka", "Jakob Gračanin"),
    NAPREDNE_TEHNIKE_PROGRAMIRANJA("Napredne tehnike programiranja", "Željko Kovačević"),
    NAPREDNE_TEME_RACUNALNIH_MREZA("Napredne teme računalnih mreža", "Dunja Bjelobrk Knežević"),
    NAPREDNO_JAVASCRIPT_PROGRAMIRANJE("Napredno JavaScript programiranje", "Ognjen Staničić"),
    NUMERICKA_MATEMATIKA("Numerička matematika", "Anđa Valent"),
    OBJEKTNO_ORIJENTIRANO_PROGRAMIRANJE("Objektno orijentirano programiranje", "Željko Kovačević"),
    OBLIKOVANJE_E_LITERATURE("Oblikovanje e literature", "Maja Turčić"),
    OBLIKOVANJE_WEB_STRANICA("Oblikovanje web stranica", "Maja Turčić"),
    OPERACIJSKI_SUSTAVI("Operacijski sustav", "Davor Cafuta"),
    OSNOVE_ELEKTROTEHNIKE_I_ELEKTRONIKE("Osnove elektronike i elektrotehnike", "Željko Stojanović"),
    OTVORENE_PLATFORME_ZA_RAZVOJ_UGRADENIH_SUSTAVA("Otvorene platforme za razvoj ugrađenih sustava", "Davor Cafuta"),
    PRIMJENA_RACUNALA("Primjena računala", "Danijela Pongrac"),
    PROGRAMIRANJE("Programiranje", "Ivan Cesar"),
    PROGRAMIRANJE_U_JEZIKU_JAVA("Programiranje u jeziku Java", "Aleksander Radovan"),
    PROGRAMIRNAJE_WEB_APLIKACIJA("Programiranje web aplikacija", "Alen Šimec"),
    RACUNALA_ZA_NADZOR_I_UPRAVLJANJE_TEHNICKIM_PROCESIMA("Računalna za nadzor i upravljanje tehničkim procesima", "Goran Malčić"),
    RACUNALNE_MREZE("Računalne mreže", "Dunja Bjelobrk Knežević"),
    RAZVOJ_APLIKACIJA_NA_ANDROID_PLATORMI("Razvoj aplikacija na Android platformi", "Renata Kramberger"),
    RAZVOJ_RACUNALNIH_IGARA("Razvoj računalnih igara", "Renata Kramberger"),
    SIGURNOST_RACUNALNIH_SUSTAVA("Sigurnost računalnih sustava", "Davor Cafuta"),
    STRUCNA_PRAKSA("Stručna praksa", "Ida Popčević"),
    UVOD_U_UNIX_SUSTAVE("Uvod u UNIX sustave", "Ivica Dodig"),
    UVOD_U_WEB_TEHNOLOGIJE("Uvod u web tehnologije", "Sanja Kraljević"),
    VJEROJATNOST_I_STATISTIKA("Vjerojatnost i statistika", "Igor Urbiha"),
    WEB_APLIKACIJE_U_JAVI("Web aplikacije u Javi", "Aleksander Radovan"),
    ZAVRSNI_RAD("Završni rad", "Vedran Tadić");


    private final String name;
    private final String professorName;

    Subject(String name, String professorName) {
        this.name = name;
        this.professorName = professorName;
    }

    public static List<String> getAllSubjects(){
        List<String> subjects = Arrays.stream(Subject.values())
                .map(subject -> subject.name)
                .collect(Collectors.toList());
        return subjects;
    }

    public static Subject findEnumByName(String selectedSubject){
        Subject subjectFound = null;
        for (Subject subject: Subject.values()){
            if (subject.name.equals(selectedSubject)){
                subjectFound = subject;
                break;
            }
        }
        return subjectFound;
    }


    public String getName() {
        return name;
    }

    public String getProfessorName() {
        return professorName;
    }

    @Override
    public String toString() {
        return super.toString();
    }


}
