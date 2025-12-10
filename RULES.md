# AI Assistant Rules for M4CartPrototype

## Project Overview

- **Project Name**: M4CartPrototype
- **Type**: Android Application (Instacart Caper Design Tools)
- **Package**: `com.instacart.caper.designtools`
- **Language**: Kotlin
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 36
- **Java Version**: 11

---

## Code Style & Standards

### Kotlin Conventions

1. **File Naming**: Use PascalCase for class files (e.g., `MainActivity.kt`)
2. **Package Structure**: Follow standard Android package structure:
    - `ui/` - UI components (Activities, Fragments, Composables)
    - `data/` - Data models, repositories, data sources
    - `domain/` - Business logic, use cases
    - `util/` - Utility classes and extensions
    - `di/` - Dependency injection modules

3. **Naming Conventions**:
    - Classes/Objects: PascalCase
    - Functions/Variables: camelCase
    - Constants: UPPER_SNAKE_CASE
    - Private properties: prefix with underscore `_propertyName` (for backing properties)

4. **Code Organization**:
    - Keep files under 300 lines when possible
    - One public class per file
    - Group related functions together
    - Use extension functions for utility operations

### Android Specific

1. **Architecture**: Follow Android architecture guidelines (MVVM/MVI preferred)
2. **Resources**:
    - Use meaningful resource names with prefixes (e.g., `activity_main`, `fragment_cart`)
    - Keep strings in `strings.xml` (no hardcoded strings)
    - Use dimensions in `dimens.xml`
    - Use colors in `colors.xml` with semantic names

3. **Dependencies**:
    - Use version catalog (`libs.versions.toml`) for dependency management
    - Prefer AndroidX libraries over legacy support libraries
    - Keep dependencies up to date

### Jetpack Compose

1. **Preview Requirements**:
   - **All Compose screens MUST include BOTH `@Fire10Preview` and `@Fire11Preview` annotations**
   - Custom annotations located at: `com.instacart.caper.designtools.ui.Fire10Preview` and
     `com.instacart.caper.designtools.ui.Fire11Preview`
   - **Fire 10 Configuration**:
      - Resolution: 1920x1200
     - DPI: 224
      - Orientation: Landscape
   - **Fire 11 Configuration**:
      - Resolution: 2000x1200
      - DPI: 213
      - Orientation: Landscape
   - Example:
    ```kotlin
    @Fire10Preview
    @Composable
    fun ScreenNamePreview() {
        ScreenName()
    }
    
    @Fire11Preview
    @Composable
    fun ScreenNamePreview() {
        ScreenName()
    }
    ```

2. **Composable Naming**:
   - Screen composables: `ScreenNameScreen` (e.g., `CartScreen`)
   - Component composables: Descriptive names (e.g., `ProductCard`, `CheckoutButton`)
   - Preview functions: `{ComposableName}Fire10Preview` and `{ComposableName}Fire11Preview` (e.g.,
     `CartScreenFire10Preview`, `CartScreenFire11Preview`)

3. **State Management**:
   - Hoist state to appropriate level
   - Use `remember` for UI state
   - Use ViewModel for business logic state
   - Pass events up, state down

4. **Modifiers**:
   - Always accept a `modifier` parameter in reusable composables
   - Apply modifier as the first parameter in the composable
   - Chain modifiers in logical order (size → padding → styling)

---

## Development Workflow

### Before Making Changes

1. **Analyze existing code** structure and patterns
2. **Check for similar implementations** in the codebase
3. **Verify compatibility** with target SDK and min SDK
4. **Consider performance** implications for Android devices

### When Adding Features

1. Create necessary layout files first
2. Implement ViewModels/business logic
3. Add Activities/Fragments/Composables
4. Update manifest if needed
5. Add appropriate tests
6. Update documentation

### When Fixing Bugs

1. Identify root cause in existing code
2. Check for similar issues elsewhere
3. Fix the issue following existing patterns
4. Add regression tests if applicable

---

## Testing Requirements

### Unit Tests

- Place in `app/src/test/java/`
- Test naming: `methodName_scenario_expectedResult`
- Use JUnit for unit tests
- Mock external dependencies

### Instrumentation Tests

- Place in `app/src/androidTest/java/`
- Test actual Android framework components
- Use Espresso for UI tests

---

## Documentation Standards

### Code Comments

1. **KDoc** for public APIs:

```kotlin
/**
 * Brief description of the function.
 *
 * @param paramName Description of parameter
 * @return Description of return value
 */
```

2. **Inline comments** for complex logic only
3. **TODOs** should include assignee and context:

```kotlin
// TODO(username): Add error handling for network failures
```

### File Headers

- No need for copyright headers unless specified
- Include a brief file description for complex classes

---

## Security & Best Practices

### Security

1. Never commit sensitive data (API keys, credentials)
2. Use `local.properties` for local configuration
3. Implement proper certificate pinning for network calls
4. Use encrypted SharedPreferences for sensitive data

### Performance

1. Avoid memory leaks (weak references for callbacks)
2. Use ViewBinding instead of findViewById
3. Optimize layouts (reduce nesting)
4. Use RecyclerView for lists
5. Handle lifecycle properly

### Error Handling

1. Always handle nullable types explicitly
2. Use try-catch for risky operations
3. Provide user-friendly error messages
4. Log errors appropriately (use Timber or similar)

---

## AI Assistant Behavior Guidelines

### Code Generation

1. **Always check existing code** before generating new code
2. **Follow existing patterns** in the codebase
3. **Provide complete implementations**, not pseudo-code
4. **Include necessary imports** in code suggestions
5. **Consider Android lifecycle** in all implementations

### Responses

1. **Be concise** but thorough
2. **Explain the "why"**, not just the "what"
3. **Provide alternatives** when applicable
4. **Highlight breaking changes** or deprecated APIs
5. **Reference official documentation** when relevant

### File Operations

1. **Always verify file existence** before modifications
2. **Preserve existing code** style and formatting
3. **Update related files** (manifest, gradle, resources)
4. **Clean up unused imports** and resources

### Problem Solving

1. **Ask clarifying questions** if requirements are ambiguous
2. **Propose solutions** with trade-offs
3. **Consider edge cases** and error scenarios
4. **Prioritize maintainability** over cleverness

---

## Specific Project Rules

### For This Project (M4CartPrototype)

1. This appears to be a design tools/prototype project
2. Focus on **developer experience** and **design system implementation**
3. Code should be **clean and demonstrative**
4. Consider this may be used as a **reference implementation**

### When Adding Dependencies

1. Add to `libs.versions.toml` first
2. Update `app/build.gradle.kts`
3. Sync gradle and verify build
4. Document why the dependency is needed

### When Modifying Gradle Files

1. Keep Kotlin DSL syntax
2. Maintain alphabetical ordering where possible
3. Group related configurations
4. Test build after changes

---

## Common Tasks Reference

### Creating a New Activity

1. Create layout XML in `res/layout/`
2. Create Activity class in appropriate package
3. Add to `AndroidManifest.xml`
4. Implement lifecycle methods
5. Add navigation if needed

### Creating a New Fragment

1. Create layout XML
2. Create Fragment class
3. Handle arguments via Bundle
4. Implement proper lifecycle handling
5. Add to navigation graph if using Navigation component

### Adding a New Dependency

1. Add version to `libs.versions.toml` under `[versions]`
2. Add library to `[libraries]` section
3. Reference in `build.gradle.kts` using `implementation(libs.library.name)`
4. Sync and rebuild

---

## Questions to Ask

Before implementing features, consider:

- [ ] Does this follow Android best practices?
- [ ] Is this compatible with minSdk 24?
- [ ] Will this work on different screen sizes?
- [ ] Is this testable?
- [ ] Does this handle configuration changes?
- [ ] Are there accessibility considerations?
- [ ] Is error handling implemented?
- [ ] Are resources properly named and organized?

---

## Prohibited Practices

❌ Hardcoded strings in code
❌ Memory leaks (context leaks, listener leaks)
❌ findViewById (use ViewBinding instead)
❌ Deprecated APIs without suppression and justification
❌ Blocking main thread with heavy operations
❌ Ignoring lint warnings without good reason
❌ Committing commented-out code
❌ Magic numbers (use constants or resources)

---

## Preferred Patterns

✅ Use ViewBinding or Jetpack Compose for UI
✅ Use Kotlin coroutines for async operations
✅ Use sealed classes for state management
✅ Use data classes for models
✅ Use extension functions for utilities
✅ Use lazy initialization where appropriate
✅ Use when expressions over if-else chains
✅ Use nullable types explicitly
✅ Use meaningful variable names

---

## Version Control

### Commit Messages

- Use conventional commits format
- Examples:
    - `feat: Add cart item selection`
    - `fix: Resolve crash on rotation`
    - `refactor: Extract cart logic to repository`
    - `docs: Update README with setup instructions`

---

## Notes

- This file should be referenced at the start of AI assistant sessions
- Update this file as project conventions evolve
- Treat this as a living document
- Team members should contribute to this file

---

**Last Updated**: [Date will be auto-updated]
**Maintained By**: Project Team
