#ifdef GL_ES
  precision mediump float;
#endif
uniform vec4 u_tint;
varying vec4 v_position;

void main()
{
	gl_FragColor = u_tint;
}
