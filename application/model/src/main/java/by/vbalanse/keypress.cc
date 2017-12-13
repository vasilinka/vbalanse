#include <node.h>
#include <v8.h>
#include <stdio.h>
#include <stdlib.h>
 
#ifdef OSX
 
#include <CoreFoundation/CoreFoundation.h>
#include <Carbon/Carbon.h> /* For kVK_ constants, and TIS functions. */
 
#endif
 
#ifdef GTK
// hmm. soon.
#endif 
 
 
using namespace v8;
 
#ifdef OSX
 
CFStringRef createStringForKey(CGKeyCode keyCode)
{
	TISInputSourceRef currentKeyboard = TISCopyCurrentKeyboardInputSource();
	CFDataRef layoutData = (CFDataRef) TISGetInputSourceProperty(currentKeyboard, kTISPropertyUnicodeKeyLayoutData);
	const UCKeyboardLayout *keyboardLayout = (const UCKeyboardLayout *)CFDataGetBytePtr(layoutData);
 
	UInt32 keysDown = 0;
	UniChar chars[4];
	UniCharCount realLength;
 
	UCKeyTranslate(keyboardLayout,
	               keyCode,
	               kUCKeyActionDisplay,
	               0,
	               LMGetKbdType(),
	               kUCKeyTranslateNoDeadKeysBit,
	               &keysDown,
	               sizeof(chars) / sizeof(chars[0]),
	               &realLength,
	               chars);
	CFRelease(currentKeyboard);    
	return CFStringCreateWithCharacters(kCFAllocatorDefault, chars, 1);
}
 
CGKeyCode keyCodeForChar(const char c)
{
	static CFMutableDictionaryRef charToCodeDict = NULL;
	CGKeyCode code;
	UniChar character = c;
	CFStringRef charStr = NULL;
 
	if (charToCodeDict == NULL) {
	    size_t i;
	    charToCodeDict = CFDictionaryCreateMutable(kCFAllocatorDefault,
	                                               128,
	                                               &kCFCopyStringDictionaryKeyCallBacks,
	                                               NULL);
	    if (charToCodeDict == NULL) return UINT16_MAX;
 
	    /* Loop through every keycode (0 - 127) to find its current mapping. */
	    for (i = 0; i < 128; ++i) {
	        CFStringRef string = createStringForKey((CGKeyCode)i);
	        if (string != NULL) {
	            CFDictionaryAddValue(charToCodeDict, string, (const void *)i);
	            CFRelease(string);
	        }
	    }
	}
 
	charStr = CFStringCreateWithCharacters(kCFAllocatorDefault, &character, 1);
	if (!CFDictionaryGetValueIfPresent(charToCodeDict, charStr,
	                                   (const void **)&code)) {
	    code = UINT16_MAX;
	}
 
	CFRelease(charStr);
	return code;
}
 
#endif
 
Handle<Value> callKeyEvent(const Arguments& args) {
    HandleScope scope;
    
   if (args.Length() < 1) {
        ThrowException(Exception::TypeError(String::New("Wrong number of arguments")));
        return scope.Close(Undefined());
    }
 
    if (!args[0]->IsString()) {
        ThrowException(Exception::TypeError(String::New("Wrong arguments")));
        return scope.Close(Undefined());
    }
 
    Local<Object> obj = Object::New();
    // obj->Set(String::NewSymbol("x"), Number::New( 1 + (rand() % xBound->IntegerValue() )));
    // obj->Set(String::NewSymbol("y"), Number::New( 1 + (rand() % yBound->IntegerValue() )));
 
    Handle<Value> arg = args[0];
  	String::AsciiValue value(arg);
#ifdef OSX
    CGKeyCode k = keyCodeForChar( (char) (*value)[0] );
 
    CGEventRef e = CGEventCreateKeyboardEvent (NULL, k, true);
    CGEventPost(kCGSessionEventTap, e);
    CFRelease(e);
#endif
    return scope.Close(obj);
}
 
void init(Handle<Object> target) {
    target->Set(String::NewSymbol("callKeyEvent"),
        FunctionTemplate::New(callKeyEvent)->GetFunction());
}
NODE_MODULE(keypress, init)