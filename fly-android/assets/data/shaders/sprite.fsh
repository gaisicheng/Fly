#ifdef GL_ES
  precision mediump float;
#endif

uniform vec3 u_lightPos;

varying vec4 v_position;

void main()
{
	float len = length(v_position-u_lightPos)/10;
	gl_FragColor = vec4(0,1,0,1) * len;
}
