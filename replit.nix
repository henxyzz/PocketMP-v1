{ pkgs }: {
    deps = [
      pkgs.gradle
      pkgs.gh
        pkgs.graalvm17-ce
        pkgs.maven
        pkgs.replitPackages.jdt-language-server
        pkgs.replitPackages.java-debug
    ];
}