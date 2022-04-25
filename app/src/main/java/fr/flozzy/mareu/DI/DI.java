package fr.flozzy.mareu.DI;

import fr.flozzy.mareu.SERVICE.DummyMaReuApiService;
import fr.flozzy.mareu.SERVICE.MaReuApiService;

public class DI {

    private static final MaReuApiService service = new DummyMaReuApiService();

    /**
     * Get an instance on @{@link MaReuApiService}
     *
     * @return service
     */
    public static MaReuApiService getMaReuApiService() {
        return service;
    }

    /**
     * Get always a new instance on @{@link MaReuApiService}. Useful for tests, so we ensure the context is clean.
     *
     * @return DummyMaReuApiService
     */
    public static MaReuApiService getNewInstanceApiService() {
        return new DummyMaReuApiService();
    }
}