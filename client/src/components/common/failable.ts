
/**
 * A type that represents a value that can fail, but always has a value (default one)
 * 
 * ### Wrapping
 * ```ts
 * {isOk: true, value: myInt} // happy path
 * {isOk: false, value: 0}    // unhappy path with default value
 * ```
 * ### Unwrapping
 * ```ts
 * function fails(x: Fails<number>) {
 *     let value = x.value;
 *     if (x.isOk) {
 *         console.log("value is " + value);
 *     } else {
 *         console.log("default value is " + value)
 *     } 
 * }
 * ```
 */
export type Fails<T> = ({isOk: true, value: T }) | ({isOk: false, message?: string, value: T});

/**
 * A type that has either a value or an error.
 * 
 * ### Wrapping
 * ```ts
 * {isOk: true, value: myInt} // happy path
 * {isOk: true, error: {message: "something went wrong"}} // unhappy path
 * ```
 * 
 * ### unwrapping
 * ```ts
 * function result(x: Result<number, {message: string}>) {
 *    if (x.isOk) {
 *        console.log("value is " + x.value);
 *   } else {
 *        console.log("an error occured: " + x.error);
 *   }
 * }
 * ```
 */
export type Result<T, E> = ({value: T, isOk: true }) | ({error: E, isOk: false});

/**
 * A type that has either a value or nothing.
 * 
 * ### wrapping
 * ```ts
 * {isSome: true, value: myInt} // happy path
 * {isSome: false} // unhappy path
 * ```
 * ### unwrapping
 * 
 * ```ts
 * function option(x: Option<number>) {
 *    if (x.isSome) {
 *       console.log("value is " + x.value);
 *    } else {
 *       console.log("no value");
 *    }
 * ```
 */
export type Option<T> = ({value: T, isSome: true }) | ({isSome: false });