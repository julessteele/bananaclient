#version 150

in vec3 Position;
in vec4 Color;

uniform mat4 ProjectionMatrix;
uniform mat4 ViewMatrix;

out vec4 vColor;

void main() {
    gl_Position = ProjectionMatrix * ViewMatrix * vec4(Position, 1.0);
    vColor = Color;
}
