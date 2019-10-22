package kazoo.controller;

public class Endpoints {

    public class Autenticacion {
        public static final String AUTENTICACION_BASE = "/usuario";
        public static final String REGISTRAR = AUTENTICACION_BASE + "/registrar";
        public static final String LOGIN = AUTENTICACION_BASE + "/login";
    }

    public class Partitura {
        public static final String PARTITURA_BASE = "/partitura";
        public static final String PARTITURA = PARTITURA_BASE + "/{id}";
        public static final String PUBLICAR = PARTITURA_BASE + "/publicar";
    }
}
