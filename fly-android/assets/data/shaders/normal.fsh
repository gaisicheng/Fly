#ifdef GL_ES
  precision mediump float;
#endif
  uniform vec4 diffuseColor;       // The position of the light in eye space.
  uniform vec4 backgroundColor;       // The position of the light in eye space.
varying vec4 v_position;

float interpolate (float a, float b, float stage, float gradient)
{
	return a + ((b - a) * stage / gradient);
}

void main()
{
	gl_FragColor = diffuseColor;
}
