import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class testingCountryList {

    private String mode;
    private String continent;

    private Country[] listOfCountries;


    public testingCountryList(String mode, String continent) {
        this.mode = mode;
        this.continent = continent;
        if (Objects.equals(mode, "Global Mode")) {
            listOfCountries = CountryListGlobalorMicro("Global Mode");
        } else if (Objects.equals(mode, "Micro Nation Mode")) {
            listOfCountries = CountryListGlobalorMicro("Micro Nation Mode");
        } else if (Objects.equals(mode, "Continental Mode")) {
            listOfCountries = CountryListContinent(continent);
            //listOfCountries = CountryList("Micro Nation Mode", continent);
        } else {
            //Add exception handling here;
        }

    }


    private Country[] CountryListGlobalorMicro(String mode) {
//        Country[] array
        Map<String, Map<String, String>> countriesWithGlobalModeYes = CountryDatabase.getCountriesWithColumnYes(mode);
        int size = countriesWithGlobalModeYes.size();
        int index = 0;
        Country[] array = new Country[size];
        for (Map.Entry<String, Map<String, String>> entry : countriesWithGlobalModeYes.entrySet()) {
            String countryName = entry.getKey();
            Country country = new Country(countryName);
            array[index++] = country;

        }
        return array;
//    private Country[] listOfCountry{
//
   }

    private Country[] CountryListContinent(String mode) {
//        Country[] array
        Map<String, Map<String, String>> countriesW = CountryDatabase.getCountriesWithContinentModeAndContinent(mode);
        int size = countriesW.size();
        int index = 0;
        Country[] array = new Country[size];
        for (Map.Entry<String, Map<String, String>> entry : countriesW.entrySet()) {
            String countryName = entry.getKey();
            Country country = new Country(countryName);
            array[index++] = country;

        }
        return array;
//    private Country[] listOfCountry{
//
    }

    public static void main(String[] args) {
        testingCountryList test = new testingCountryList("Global Mode", null);
        testingCountryList test2 = new testingCountryList("Continental Mode", "Europe");
        System.out.println(Arrays.toString(test2.listOfCountries));
        for (int i =0; i<test2.listOfCountries.length;i++){
            Country countrytest = test2.listOfCountries[i];
            System.out.println(countrytest.getName());
        }

    }
}
