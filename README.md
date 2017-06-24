
# Projetos de OpenGL

O projeto contém as atividades e TDE da segunda parcial da disciplina Programação de Jogos 3D
Algumas classes como Camera, DirectionalLight e POintLight estão alteradas para se encaixar nas demandas do TDE.

## Atividades

As atividades são compiladas nas respectivas classes.

* Atividade 4 : Pentagon.java
* Atividade 5 : RotatingCube.java
* Atividade 6 : TerrainLoader.java
* Atividade 7 : TerrainLoader.java
* Atividade 8 : LitCube.java


## TDE

O TDE consiste nas seguintes classes:
* TerrainPerlinNoise.java (compilável)
* Noise.java
* PhongDir.frag
* PhingDir.vert

### Controles

* L : Aumenta o relevo
* K : Diminui o relevo
* P : Alterna entre wireframe e fill
* W e S : movimento para frente e para trás (moveFront)
* Z e C : movimento para os lados (strifeLeft, strifeRight)
* Botões de seta: rotação da câmera

### Como alterar as dimensões do terreno e o seed

Acesse o arquivo 
```
DATA.txt
```
Modifique os valores por valores inteiros. A primeira linha corresponde à largura do terreno, a segunda à profundidade, e a terceira ao seed que vai gerar valores aleatórios para o Perlin Noise.

