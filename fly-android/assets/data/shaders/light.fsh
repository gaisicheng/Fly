precision mediump float;       // Set the default precision to medium. We don't need as high of a
                               // precision in the fragment shader.
uniform vec3 u_lightPos;       // The position of the light in eye space.
uniform vec4 diffuseColor;     // The position of the light in eye space.

varying float v_diffuse;               // Interpolated normal for this fragment.
varying float v_position;
// fog

uniform float sight;

// The entry point for our fragment shader.
void main()
{
	float distance  = length(u_lightPos.x - v_position.x);

	float factor = distance / sight;
    // Multiply the color by the diffuse illumination level to get final output color.
    gl_FragColor =  mix(diffuseColor * v_diffuse, vec4(1,1,1,1), factor);
}
