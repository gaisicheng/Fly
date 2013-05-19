precision mediump float;       // Set the default precision to medium. We don't need as high of a
                               // precision in the fragment shader.
uniform vec3 u_lightPos;       // The position of the light in eye space.
uniform vec4 u_tint;           // The overlaying color.

varying vec4 v_position;       // Interpolated position for this fragment.
varying vec4 v_color;          // This is the color from the vertex shader interpolated across the
                               // triangle per fragment.
varying vec3 v_normal;         // Interpolated normal for this fragment.

// The entry point for our fragment shader.
void main()
{
    // Will be used for attenuation.
    float distance = length(u_lightPos - v_position);

    // Get a lighting direction vector from the light to the vertex.
    vec3 lightVector = normalize(u_lightPos - v_position);

    float diffuse = (7 - distance) / 10.0f;

    gl_FragColor = u_tint * diffuse ;

}
