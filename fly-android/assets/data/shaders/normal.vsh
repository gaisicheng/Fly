attribute vec4 a_position;
attribute vec4 a_color;
attribute vec3 a_normal;

uniform mat4 u_MVPMatrix;

varying vec4 v_position;
varying vec3 v_normal;

void main()
{
	v_position = a_position;
   gl_Position =  u_MVPMatrix * a_position;
}
