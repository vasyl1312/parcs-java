all: run

clean:
	rm -f out/*.jar

out/WilsonTest.jar: out/parcs.jar src/WilsonTest.java
	@javac -cp out/parcs.jar --release 11 -d out src/WilsonTest.java
	@jar cf out/WilsonTest.jar -C out WilsonTest.class
	@rm -f out/WilsonTest.class

out/WilsonTestApplication.jar: out/parcs.jar src/WilsonTestApplication.java
	@javac -cp out/parcs.jar --release 11 -d out src/WilsonTestApplication.java
	@jar cf out/WilsonTestApplication.jar -C out WilsonTestApplication.class
	@rm -f out/WilsonTestApplication.class

build: out/WilsonTest.jar out/WilsonTestApplication.jar

run: out/WilsonTestApplication.jar out/WilsonTest.jar
	@cd out && java -cp "WilsonTestApplication.jar:parcs.jar" WilsonTestApplication
