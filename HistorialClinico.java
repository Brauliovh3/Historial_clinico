import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// ══════════════════════════════════════════════════════
//  CLÍNICA IBEROAMERICANA - Sistema de Historial Clínico
// ══════════════════════════════════════════════════════

// ─── MODELO: Paciente ─────────────────────────────────
class Paciente {
    private static int contadorId = 1;

    private int    id;
    private String nombre;
    private int    edad;
    private String genero;
    private String telefono;
    private String direccion;

    public Paciente(String nombre, int edad, String genero, String telefono, String direccion) {
        this.id        = contadorId++;
        this.nombre    = nombre;
        this.edad      = edad;
        this.genero    = genero;
        this.telefono  = telefono;
        this.direccion = direccion;
    }

    public int    getId()        { return id; }
    public String getNombre()    { return nombre; }
    public int    getEdad()      { return edad; }
    public String getGenero()    { return genero; }
    public String getTelefono()  { return telefono; }
    public String getDireccion() { return direccion; }

    public void setNombre(String n)    { this.nombre    = n; }
    public void setEdad(int e)         { this.edad      = e; }
    public void setTelefono(String t)  { this.telefono  = t; }
    public void setDireccion(String d) { this.direccion = d; }

    @Override
    public String toString() {
        return String.format("[ID: %d] %-25s | %3d anios | %-6s | Tel: %s",
                id, nombre, edad, genero, telefono);
    }
}

// ─── MODELO: Medicamento ──────────────────────────────
class Medicamento {
    private String nombre;
    private String dosis;
    private String frecuencia;
    private int    duracionDias;

    public Medicamento(String nombre, String dosis, String frecuencia, int duracionDias) {
        this.nombre       = nombre;
        this.dosis        = dosis;
        this.frecuencia   = frecuencia;
        this.duracionDias = duracionDias;
    }

    @Override
    public String toString() {
        return String.format("      * %s %s  -- cada %s  por %d dias",
                nombre, dosis, frecuencia, duracionDias);
    }
}

// ─── MODELO: Diagnostico ──────────────────────────────
class Diagnostico {
    private String codigo;
    private String descripcion;
    private String observaciones;

    public Diagnostico(String codigo, String descripcion, String observaciones) {
        this.codigo        = codigo;
        this.descripcion   = descripcion;
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return String.format("      [%s] %s\n           Obs: %s",
                codigo, descripcion, observaciones);
    }
}

// ─── MODELO: ResultadoExamen ──────────────────────────
class ResultadoExamen {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private String    tipo;
    private String    descripcion;
    private String    valor;
    private String    unidad;
    private LocalDate fecha;

    public ResultadoExamen(String tipo, String descripcion, String valor, String unidad) {
        this.tipo        = tipo;
        this.descripcion = descripcion;
        this.valor       = valor;
        this.unidad      = unidad;
        this.fecha       = LocalDate.now();
    }

    @Override
    public String toString() {
        return String.format("      [%s] %s: %s %s  (%s)",
                tipo, descripcion, valor, unidad, fecha.format(FMT));
    }
}

// ─── MODELO: Consulta ─────────────────────────────────
class Consulta {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static int contadorConsulta = 1;

    private int                   numero;
    private LocalDate             fecha;
    private String                motivo;
    private String                medico;
    private List<Diagnostico>     diagnosticos;
    private List<Medicamento>     medicamentos;
    private List<ResultadoExamen> resultados;
    private String                notas;

    public Consulta(String motivo, String medico) {
        this.numero       = contadorConsulta++;
        this.fecha        = LocalDate.now();
        this.motivo       = motivo;
        this.medico       = medico;
        this.diagnosticos = new ArrayList<>();
        this.medicamentos = new ArrayList<>();
        this.resultados   = new ArrayList<>();
        this.notas        = "";
    }

    public int                   getNumero()      { return numero;      }
    public LocalDate             getFecha()       { return fecha;       }
    public List<Diagnostico>     getDiagnosticos(){ return diagnosticos; }
    public List<Medicamento>     getMedicamentos() { return medicamentos; }
    public List<ResultadoExamen> getResultados()  { return resultados;  }

    public void agregarDiagnostico(Diagnostico d)  { diagnosticos.add(d); }
    public void agregarMedicamento(Medicamento m)  { medicamentos.add(m); }
    public void agregarResultado(ResultadoExamen r){ resultados.add(r);   }
    public void setNotas(String n) { this.notas = n; }

    public void mostrar() {
        System.out.println("  +-- Consulta #" + numero + "  -----  " + fecha.format(FMT));
        System.out.println("  |  Motivo : " + motivo);
        System.out.println("  |  Medico : " + medico);
        if (!diagnosticos.isEmpty()) {
            System.out.println("  |  Diagnosticos:");
            diagnosticos.forEach(d -> System.out.println("  |" + d));
        }
        if (!medicamentos.isEmpty()) {
            System.out.println("  |  Medicamentos recetados:");
            medicamentos.forEach(m -> System.out.println("  |" + m));
        }
        if (!resultados.isEmpty()) {
            System.out.println("  |  Resultados de examenes:");
            resultados.forEach(r -> System.out.println("  |" + r));
        }
        if (!notas.isBlank()) {
            System.out.println("  |  Notas: " + notas);
        }
        System.out.println("  +--------------------------------------------------");
    }
}

// ─── MODELO: HistorialClinico ─────────────────────────
class HistorialClinico {
    private Paciente       paciente;
    private List<Consulta> consultas;

    public HistorialClinico(Paciente paciente) {
        this.paciente  = paciente;
        this.consultas = new ArrayList<>();
    }

    public Paciente       getPaciente()  { return paciente;  }
    public List<Consulta> getConsultas() { return consultas; }
    public void agregarConsulta(Consulta c) { consultas.add(c); }

    public void mostrarCompleto() {
        System.out.println("\n================================================");
        System.out.println("   HISTORIAL CLINICO -- CLINICA IBEROAMERICANA  ");
        System.out.println("================================================");
        System.out.println("  Paciente  : " + paciente.getNombre());
        System.out.println("  ID        : " + paciente.getId());
        System.out.println("  Edad      : " + paciente.getEdad() + " anios");
        System.out.println("  Genero    : " + paciente.getGenero());
        System.out.println("  Telefono  : " + paciente.getTelefono());
        System.out.println("  Direccion : " + paciente.getDireccion());
        System.out.println("  Consultas : " + consultas.size());
        System.out.println("------------------------------------------------");
        if (consultas.isEmpty()) {
            System.out.println("  Sin consultas registradas.");
        } else {
            consultas.forEach(Consulta::mostrar);
        }
    }
}

// ══════════════════════════════════════════════════════
//  GESTOR PRINCIPAL
// ══════════════════════════════════════════════════════
class GestorHistorialClinico {
    private List<Paciente>                pacientes;
    private Map<Integer, HistorialClinico> historiales;
    private Scanner sc;

    public GestorHistorialClinico() {
        pacientes   = new ArrayList<>();
        historiales = new HashMap<>();
        sc          = new Scanner(System.in);
    }

    // ── Helpers ───────────────────────────────────────

    private String leerTexto(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    private int leerEntero(String prompt) {
        while (true) {
            System.out.print(prompt);
            try { return Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) {
                System.out.println("  [!] Ingresa un numero valido.");
            }
        }
    }

    private void pausar() {
        System.out.print("\n  Presiona ENTER para continuar...");
        sc.nextLine();
    }

    private void encabezado(String titulo) {
        System.out.println("\n================================================");
        System.out.printf( "  CLINICA IBEROAMERICANA -- %s%n", titulo);
        System.out.println("================================================");
    }

    // ── Menu Principal ────────────────────────────────

    public void iniciar() {
        System.out.println();
        System.out.println("  ================================================");
        System.out.println("                                                  ");
        System.out.println("         CLINICA IBEROAMERICANA                   ");
        System.out.println("     Sistema de Historiales Clinicos v1.0         ");
        System.out.println("                                                  ");
        System.out.println("  ================================================");

        boolean continuar = true;
        while (continuar) {
            System.out.println("\n  +-------------------------------+");
            System.out.println("  |       MENU PRINCIPAL          |");
            System.out.println("  +-------------------------------+");
            System.out.println("  |  1. Gestion de pacientes      |");
            System.out.println("  |  2. Gestion de consultas      |");
            System.out.println("  |  3. Resultados de examenes    |");
            System.out.println("  |  4. Ver historial clinico     |");
            System.out.println("  |  5. Buscar paciente           |");
            System.out.println("  |  0. Salir                     |");
            System.out.println("  +-------------------------------+");

            switch (leerEntero("  Opcion: ")) {
                case 1 -> menuPacientes();
                case 2 -> menuConsultas();
                case 3 -> menuResultados();
                case 4 -> menuVerHistorial();
                case 5 -> buscarPaciente();
                case 0 -> {
                    System.out.println("\n  Gracias por usar el sistema. Hasta pronto!\n");
                    continuar = false;
                }
                default -> System.out.println("  [!] Opcion invalida.");
            }
        }
    }

    // ── Gestion de Pacientes ──────────────────────────

    private void menuPacientes() {
        boolean volver = false;
        while (!volver) {
            encabezado("PACIENTES");
            System.out.println("  1. Registrar nuevo paciente");
            System.out.println("  2. Listar todos los pacientes");
            System.out.println("  3. Editar datos de paciente");
            System.out.println("  4. Eliminar paciente");
            System.out.println("  0. Volver al menu principal");

            switch (leerEntero("  Opcion: ")) {
                case 1 -> registrarPaciente();
                case 2 -> listarPacientes();
                case 3 -> editarPaciente();
                case 4 -> eliminarPaciente();
                case 0 -> volver = true;
                default -> System.out.println("  [!] Opcion invalida.");
            }
        }
    }

    private void registrarPaciente() {
        encabezado("REGISTRAR PACIENTE");
        String nombre    = leerTexto("  Nombre completo   : ");
        int    edad      = leerEntero("  Edad              : ");
        String genero    = leerTexto("  Genero (M/F/Otro)  : ");
        String telefono  = leerTexto("  Telefono          : ");
        String direccion = leerTexto("  Direccion         : ");

        Paciente p = new Paciente(nombre, edad, genero, telefono, direccion);
        pacientes.add(p);
        historiales.put(p.getId(), new HistorialClinico(p));

        System.out.println("\n  [OK] Paciente registrado con ID: " + p.getId());
        pausar();
    }

    private void listarPacientes() {
        encabezado("LISTA DE PACIENTES");
        if (pacientes.isEmpty()) {
            System.out.println("  Sin pacientes registrados.");
        } else {
            System.out.println("  " + "-".repeat(65));
            pacientes.forEach(p -> System.out.println("  " + p));
            System.out.println("  " + "-".repeat(65));
            System.out.println("  Total: " + pacientes.size() + " paciente(s).");
        }
        pausar();
    }

    private Paciente seleccionarPaciente() {
        if (pacientes.isEmpty()) {
            System.out.println("  [!] No hay pacientes registrados.");
            return null;
        }
        System.out.println("\n  Pacientes disponibles:");
        pacientes.forEach(p -> System.out.println("    " + p));
        int id = leerEntero("  ID del paciente: ");
        Paciente p = pacientes.stream()
                .filter(x -> x.getId() == id)
                .findFirst().orElse(null);
        if (p == null) System.out.println("  [!] No se encontro el paciente con ID " + id + ".");
        return p;
    }

    private void editarPaciente() {
        encabezado("EDITAR PACIENTE");
        Paciente p = seleccionarPaciente();
        if (p == null) return;

        System.out.println("  Editando: " + p.getNombre());
        System.out.println("  (Dejar en blanco para conservar el valor actual)\n");

        String nombre = leerTexto("  Nuevo nombre     : ");
        if (!nombre.isBlank()) p.setNombre(nombre);

        String edadStr = leerTexto("  Nueva edad       : ");
        if (!edadStr.isBlank()) {
            try { p.setEdad(Integer.parseInt(edadStr)); }
            catch (NumberFormatException e) { System.out.println("  [!] Edad no modificada (valor invalido)."); }
        }

        String telefono = leerTexto("  Nuevo telefono   : ");
        if (!telefono.isBlank()) p.setTelefono(telefono);

        String direccion = leerTexto("  Nueva direccion  : ");
        if (!direccion.isBlank()) p.setDireccion(direccion);

        System.out.println("  [OK] Datos actualizados.");
        pausar();
    }

    private void eliminarPaciente() {
        encabezado("ELIMINAR PACIENTE");
        Paciente p = seleccionarPaciente();
        if (p == null) return;

        String conf = leerTexto("  Eliminar a " + p.getNombre() + " y todo su historial? (s/n): ");
        if (conf.equalsIgnoreCase("s")) {
            pacientes.remove(p);
            historiales.remove(p.getId());
            System.out.println("  [OK] Paciente eliminado.");
        } else {
            System.out.println("  Operacion cancelada.");
        }
        pausar();
    }

    // ── Gestion de Consultas ──────────────────────────

    private void menuConsultas() {
        boolean volver = false;
        while (!volver) {
            encabezado("CONSULTAS");
            System.out.println("  1. Registrar nueva consulta");
            System.out.println("  2. Agregar diagnostico a consulta");
            System.out.println("  3. Agregar medicamento a consulta");
            System.out.println("  4. Listar consultas de un paciente");
            System.out.println("  0. Volver al menu principal");

            switch (leerEntero("  Opcion: ")) {
                case 1 -> registrarConsulta();
                case 2 -> agregarDiagnostico();
                case 3 -> agregarMedicamento();
                case 4 -> listarConsultas();
                case 0 -> volver = true;
                default -> System.out.println("  [!] Opcion invalida.");
            }
        }
    }

    private void registrarConsulta() {
        encabezado("NUEVA CONSULTA");
        Paciente p = seleccionarPaciente();
        if (p == null) return;

        String motivo = leerTexto("  Motivo de consulta   : ");
        String medico = leerTexto("  Medico responsable   : ");
        String notas  = leerTexto("  Notas adicionales    : ");

        Consulta c = new Consulta(motivo, medico);
        c.setNotas(notas);
        historiales.get(p.getId()).agregarConsulta(c);

        System.out.println("  [OK] Consulta #" + c.getNumero() + " registrada para " + p.getNombre() + ".");
        pausar();
    }

    private Consulta seleccionarConsulta(HistorialClinico h) {
        List<Consulta> lista = h.getConsultas();
        if (lista.isEmpty()) {
            System.out.println("  [!] El paciente no tiene consultas registradas.");
            return null;
        }
        System.out.println("  Consultas del paciente:");
        lista.forEach(c -> System.out.printf("    #%d  --  %s  --  %s%n",
                c.getNumero(),
                c.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                c.getDiagnosticos().isEmpty() ? "(sin diagnostico)" : c.getDiagnosticos().size() + " diagnostico(s)"));
        int num = leerEntero("  Numero de consulta: ");
        Consulta c = lista.stream().filter(x -> x.getNumero() == num).findFirst().orElse(null);
        if (c == null) System.out.println("  [!] Consulta no encontrada.");
        return c;
    }

    private void agregarDiagnostico() {
        encabezado("AGREGAR DIAGNOSTICO");
        Paciente p = seleccionarPaciente();
        if (p == null) return;
        Consulta c = seleccionarConsulta(historiales.get(p.getId()));
        if (c == null) return;

        String codigo = leerTexto("  Codigo CIE-10       : ");
        String desc   = leerTexto("  Descripcion         : ");
        String obs    = leerTexto("  Observaciones       : ");

        c.agregarDiagnostico(new Diagnostico(codigo, desc, obs));
        System.out.println("  [OK] Diagnostico agregado.");
        pausar();
    }

    private void agregarMedicamento() {
        encabezado("AGREGAR MEDICAMENTO");
        Paciente p = seleccionarPaciente();
        if (p == null) return;
        Consulta c = seleccionarConsulta(historiales.get(p.getId()));
        if (c == null) return;

        String nombre     = leerTexto("  Nombre del medicamento : ");
        String dosis      = leerTexto("  Dosis (ej. 500mg)      : ");
        String frecuencia = leerTexto("  Frecuencia (ej. 8h)    : ");
        int    dias       = leerEntero("  Duracion (dias)        : ");

        c.agregarMedicamento(new Medicamento(nombre, dosis, frecuencia, dias));
        System.out.println("  [OK] Medicamento agregado.");
        pausar();
    }

    private void listarConsultas() {
        encabezado("CONSULTAS DEL PACIENTE");
        Paciente p = seleccionarPaciente();
        if (p == null) return;

        List<Consulta> lista = historiales.get(p.getId()).getConsultas();
        System.out.println("\n  Paciente: " + p.getNombre());
        if (lista.isEmpty()) {
            System.out.println("  Sin consultas registradas.");
        } else {
            lista.forEach(Consulta::mostrar);
        }
        pausar();
    }

    // ── Resultados de Examenes ────────────────────────

    private void menuResultados() {
        boolean volver = false;
        while (!volver) {
            encabezado("RESULTADOS DE EXAMENES");
            System.out.println("  1. Agregar resultado a consulta");
            System.out.println("  2. Ver todos los resultados de un paciente");
            System.out.println("  0. Volver al menu principal");

            switch (leerEntero("  Opcion: ")) {
                case 1 -> agregarResultado();
                case 2 -> verResultados();
                case 0 -> volver = true;
                default -> System.out.println("  [!] Opcion invalida.");
            }
        }
    }

    private void agregarResultado() {
        encabezado("AGREGAR RESULTADO DE EXAMEN");
        Paciente p = seleccionarPaciente();
        if (p == null) return;
        Consulta c = seleccionarConsulta(historiales.get(p.getId()));
        if (c == null) return;

        String tipo   = leerTexto("  Tipo (LAB/IMG/ECG/OTRO) : ");
        String desc   = leerTexto("  Descripcion del examen  : ");
        String valor  = leerTexto("  Valor / resultado        : ");
        String unidad = leerTexto("  Unidad (ej. mg/dL, ---)  : ");

        c.agregarResultado(new ResultadoExamen(tipo, desc, valor, unidad));
        System.out.println("  [OK] Resultado agregado.");
        pausar();
    }

    private void verResultados() {
        encabezado("RESULTADOS DEL PACIENTE");
        Paciente p = seleccionarPaciente();
        if (p == null) return;

        List<Consulta> lista = historiales.get(p.getId()).getConsultas();
        System.out.println("\n  Paciente: " + p.getNombre());
        boolean hayResultados = false;
        for (Consulta c : lista) {
            if (!c.getResultados().isEmpty()) {
                System.out.println("  -- Consulta #" + c.getNumero() + " --");
                c.getResultados().forEach(r -> System.out.println("  " + r));
                hayResultados = true;
            }
        }
        if (!hayResultados) System.out.println("  Sin resultados registrados.");
        pausar();
    }

    // ── Ver Historial ─────────────────────────────────

    private void menuVerHistorial() {
        encabezado("VER HISTORIAL CLINICO");
        Paciente p = seleccionarPaciente();
        if (p == null) return;
        historiales.get(p.getId()).mostrarCompleto();
        pausar();
    }

    // ── Buscar Paciente ───────────────────────────────

    private void buscarPaciente() {
        encabezado("BUSCAR PACIENTE");
        String termino = leerTexto("  Nombre o parte del nombre: ").toLowerCase();

        List<Paciente> encontrados = pacientes.stream()
                .filter(p -> p.getNombre().toLowerCase().contains(termino))
                .toList();

        if (encontrados.isEmpty()) {
            System.out.println("  [!] No se encontraron pacientes con ese nombre.");
        } else {
            System.out.println("  Resultados encontrados: " + encontrados.size());
            System.out.println("  " + "-".repeat(65));
            encontrados.forEach(p -> System.out.println("  " + p));
            System.out.println("  " + "-".repeat(65));
        }
        pausar();
    }
}

// ══════════════════════════════════════════════════════
//  CLASE PRINCIPAL
// ══════════════════════════════════════════════════════
public class Main {
    public static void main(String[] args) {
        new GestorHistorialClinico().iniciar();
    }
}
