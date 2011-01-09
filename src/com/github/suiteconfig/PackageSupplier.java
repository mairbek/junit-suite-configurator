package com.github.suiteconfig;

import com.google.common.base.Objects;
import com.google.common.base.Supplier;
import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PackageSupplier implements Supplier<Collection<Class<?>>> {
    private final String name;
    private final boolean recursive;

    public PackageSupplier(String name, boolean recursive) {
        this.name = name;
        this.recursive = recursive;
    }


    public Collection<Class<?>> get() {
        try {
            return getClasses(name);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }


    private Collection<Class<?>> getClasses(String packageName) throws ClassNotFoundException, IOException {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = Lists.newArrayList();
        List<JarFile> jars = Lists.newArrayList();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            if (isFile(resource)) {
                dirs.add(new File(resource.getFile()));
            }
            if (isJar(resource)) {
                jars.add(((JarURLConnection) resource.openConnection()).getJarFile());
            }
        }
        List<Class<?>> classes = Lists.newArrayList();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName, recursive));
        }
        for (JarFile jarFile : jars) {
            classes.addAll(findClasses(jarFile, packageName, recursive));
        }

        return classes;
    }

    private static boolean isJar(URL resource) {
        return Objects.equal("jar", resource.getProtocol());
    }

    private static boolean isFile(URL resource) {
        return Objects.equal("file", resource.getProtocol());
    }

    private static List<Class<?>> findClasses(File directory, String packageName, boolean recursive) throws ClassNotFoundException {
        List<Class<?>> classes = Lists.newArrayList();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                if (recursive) {
                    classes.addAll(findClasses(file, packageName + "." + file.getName(), recursive));
                }
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    private static List<Class<?>> findClasses(JarFile jar, String packageName, boolean recursive) throws ClassNotFoundException {
        List<Class<?>> classes = Lists.newArrayList();
        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (entry.isDirectory()) {
                continue;
            }

            String name = entry.getName();

            if (name.charAt(0) == '/') {
                name = name.substring(1);
            }

            if (!name.endsWith(".class")) {
                continue;
            }

            name = name.substring(0, name.length() - 6);

            int index = name.lastIndexOf("/");
            String currentPackage = "";
            if (index != -1) {
                currentPackage = name.substring(0, index);
                currentPackage = currentPackage.replace("/", ".");
                name = name.substring(index + 1);
            }

            if (recursive && !currentPackage.startsWith(packageName)) {
                continue;
            }

            if (!recursive && !currentPackage.equals(packageName)) {
                continue;
            }

            classes.add(Class.forName(currentPackage + '.' + name));
        }
        return classes;
    }

}
