all: run

clean:
	rm -f out/*.jar

out/WilsonTest.jar: out/parcs.jar src/WilsonTest.java
	@javac -cp out/parcs.jar --release 11 src/WilsonTest.java
	@jar cf out/WilsonTest.jar -C src WilsonTest.class
	@rm -f src/WilsonTest.class

out/WilsonTestApplication.jar: out/parcs.jar src/WilsonTestApplication.java
	@javac -cp out/parcs.jar --release 11 src/WilsonTestApplication.java
	@jar cf out/WilsonTestApplication.jar -C src WilsonTestApplication.class
	@rm -f src/WilsonTestApplication.class

build: out/WilsonTest.jar out/WilsonTestApplication.jar

run: out/WilsonTestApplication.jar out/WilsonTest.jar
	@cd out && java -cp "WilsonTestApplication.jar:parcs.jar" WilsonTestApplication