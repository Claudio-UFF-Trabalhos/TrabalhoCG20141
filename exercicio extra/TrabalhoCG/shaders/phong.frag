#version 330

smooth in vec4 theColor;

out vec4 outputColor;

void main()
{

    vec4 color = theColor;

    float cor = dot( vec4(1.0f,0.0f,0.0f,0.0f),normalize(color) );


    vec4 resultColor;

    if( cor > 0.75 )
       resultColor = vec4(1.0f,0.0f,0.0f,1.0f);
    else if( cor > 0.5 )
       resultColor = vec4(0.7f,0.0f,0.0f,1.0f);
    else if( cor > 0.25 )
        resultColor = vec4(0.35f,0.0f,0.0f,1.0f);
    else
        resultColor = vec4(0.15f,0.0f,0.0f,1.0f);
    
    
    outputColor = resultColor;
}