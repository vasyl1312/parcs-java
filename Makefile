all: run

clean:
	rm -f out/*.jar
	rm -f src/*.class

out/parcs.jar:
	@echo "Створення parcs.jar..."
	# javac -d out path/to/parcs/source/*.java
	# jar cf out/parcs.jar -C out path/to/compiled/classes

out/WilsonTest.jar: out/parcs.jar src/WilsonTest.java
	@echo "Компільовано WilsonTest.java..."
	@javac -cp out/parcs.jar --release 11 src/WilsonTest.java -d src
	@echo "Створення WilsonTest.jar..."
	@jar cf out/WilsonTest.jar -C src WilsonTest.class

out/WilsonTestApplication.jar: out/parcs.jar src/WilsonTestApplication.java
	@echo "Компільовано WilsonTestApplication.java..."
	@javac -cp out/parcs.jar --release 11 src/WilsonTestApplication.java -d src
	@echo "Створення WilsonTestApplication.jar..."
	@jar cf out/WilsonTestApplication.jar -C src WilsonTestApplication.class

build: out/WilsonTest.jar out/WilsonTestApplication.jar

run: build
	@echo "Запуск програми..."
	@cd out && java -cp "WilsonTestApplication.jar:parcs.jar:." WilsonTestApplication
