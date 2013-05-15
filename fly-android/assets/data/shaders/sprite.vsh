attribute vec4 a_position;
attribute vec4 a_color;
attribute vec3 a_normal;

uniform mat4 u_projTrans;

varying vec4 v_position;

void main()
{
	v_position = a_position;
   gl_Position =  u_projTrans * a_position;
}
