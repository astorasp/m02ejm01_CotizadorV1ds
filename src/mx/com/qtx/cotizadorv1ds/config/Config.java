package mx.com.qtx.cotizadorv1ds.config;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import mx.com.qtx.cotizadorv1ds.core.ICotizador;

public class Config {
	
	public static ICotizador getCotizador() {
		
		List<Class<?>> lstImplmICotizador = Config.buscarImplementacionesDe(ICotizador.class, "mx.com.qtx.cotizadorv1ds");
		int i = (int)(Math.random()*10000) % lstImplmICotizador.size();
		Class<?> claseCotizador = lstImplmICotizador.get(i);
		try {
			Constructor<?> constructorCotizador = claseCotizador.getDeclaredConstructor();
			ICotizador cotizador = (ICotizador) constructorCotizador.newInstance();
			return cotizador;
		} 
		catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | 
			   IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}
	
    static List<Class<?>> buscarImplementacionesDe(Class<?> interfaceClass, String nomPaquete) {
        List<Class<?>> lstImplementaciones = new ArrayList<>();
        String pathPaquete = nomPaquete.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try {
            // Iterar sobre todos los recursos del classpath para el paquete
            for (URL resourceI : Collections.list(classLoader.getResources(pathPaquete))) {
                try {
                	mostrarRecurso(resourceI);
                    File file = new File(resourceI.toURI());
                    
                    // Verificar si es un directorio válido
                    if (file.isDirectory()) {
                        buscarImplementsEnDirRecursivamente(file, nomPaquete, interfaceClass, lstImplementaciones, classLoader);
                    }
                } catch (
                		IllegalArgumentException| URISyntaxException e) {
                    // Manejar recursos que no son directorios (ej. JARs) o URIs inválidas
                    System.err.println("No se pudo procesar el recurso: " + resourceI);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return lstImplementaciones;
    }

    private static void buscarImplementsEnDirRecursivamente(File dirI, String nomPaquete, 
                                       Class<?> interfaceBuscada, List<Class<?>> lstImplementaciones,
                                       ClassLoader cargadorClases) {
    	
//    	System.out.println("processDirectory(" + dirI.getName() + ", " + nomPaquete + ", " 
//                                       + interfaceBuscada.getName() + ", " 
//    			                       + lstImplementaciones.getClass().getName() + ", "
//    			                       + cargadorClases.getClass());
    	
    	// Recorrer todos los archivos en el directorio
        for (File fileI : dirI.listFiles()) {
            try {
                 
                if (archivoEsClase(fileI)) {   // Procesar solo archivos .class
                	String nomArcI = fileI.getName();
                    agregarClaseSiImplementaInterface(nomPaquete, interfaceBuscada, lstImplementaciones, 
                    								  cargadorClases, nomArcI);
                }
                else 
                if (fileI.isDirectory()) { // Procesar subdirectorios recursivamente
                    String subPaquete = nomPaquete + "." + fileI.getName();
                    buscarImplementsEnDirRecursivamente(fileI, subPaquete, interfaceBuscada, lstImplementaciones, cargadorClases);
                }
                
            } catch (ClassNotFoundException | NoClassDefFoundError e) {
                System.err.println("Clase no encontrada: " + fileI.getName());
                e.printStackTrace();
            }
        }
    }

	private static boolean archivoEsClase(File file) {
		if(file.isFile() == false)
			return false;
		
		if(file.getName().endsWith(".class") == false)
			return false;
		
		return true;
	}

	private static void agregarClaseSiImplementaInterface(String nomPaquete, Class<?> interfaceBuscada,
			List<Class<?>> lstImplementaciones, ClassLoader cargadorClases, String nomArcI) throws ClassNotFoundException {
		
		String nomClaseI = nomPaquete + '.' 
		                 + nomArcI.substring(0, nomArcI.length() - 6);
		
		Class<?> claseI = Class.forName(nomClaseI, false, cargadorClases);
		
		if(claseI.isInterface()) //Si es interface, ignorar
			return;
		
		// Verificar si implementa la interfaz
		if (interfaceBuscada.isAssignableFrom(claseI)) {
		    lstImplementaciones.add(claseI);
		}
	}

	private static void mostrarRecurso(URL resource) {
		System.out.println("path recurso I:" + resource.getPath());
		
	}
	
	private static void processJarResource(URL jarUrl, String packageName, 
	                                      Class<?> interfaceClass, List<Class<?>> implementations) {
		
	    String jarPath = jarUrl.getPath().split("!")[0].replace("file:", "");
	    String searchPath = packageName.replace('.', '/') + "/";
	
	    try (JarFile jar = new JarFile(jarPath)) {
	        Enumeration<JarEntry> entries = jar.entries();
	        while (entries.hasMoreElements()) {
	            JarEntry entry = entries.nextElement();
	            if (entry.getName().startsWith(searchPath) && entry.getName().endsWith(".class")) {
	                String className = entry.getName()
	                    .replace("/", ".")
	                    .replace(".class", "");
	                try {
	                    Class<?> clazz = Class.forName(className);
	                    if (interfaceClass.isAssignableFrom(clazz) && !clazz.isInterface()) {
	                        implementations.add(clazz);
	                    }
	                } catch (ClassNotFoundException | NoClassDefFoundError e) {
	                    System.err.println("Error al cargar clase: " + className);
	                }
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}	
}
